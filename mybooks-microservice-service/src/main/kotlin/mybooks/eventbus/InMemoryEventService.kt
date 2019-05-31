package mybooks.eventbus

import org.springframework.beans.factory.DisposableBean
import org.springframework.stereotype.Service
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux

@Service
final class InMemoryEventService : BaseSubscriber<Event<in EventData, in EventMeta>>(), DisposableBean, EventService {

    private val publisher = DirectProcessor.create<Event<in EventData, in EventMeta>>()

    private val events = ArrayList<Event<in EventData, in EventMeta>>()

    init {
        this.publisher.subscribe(this)
    }

    override fun publishEvent(event: Event<in EventData, in EventMeta>) {
        publisher.onNext(event)
    }

    override fun streamEvents(): Flux<Event<in EventData, in EventMeta>> {
        return Flux.merge(
                Flux.fromIterable(events),
                publisher
        )
    }

    override fun hookOnNext(event: Event<in EventData, in EventMeta>) {
        events.add(event)
    }

    override fun destroy() {
        publisher.onComplete()
        cancel()
    }

}