package mybooks

import mybooks.eventbus.Event
import mybooks.eventbus.EventStream
import mybooks.eventbus.data.EventData
import mybooks.eventbus.meta.EventMeta
import org.springframework.beans.factory.DisposableBean
import org.springframework.stereotype.Service
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux
import kotlin.reflect.KClass

@Service
final class InMemoryEventStream : BaseSubscriber<Event<in EventData, in EventMeta>>(), DisposableBean, EventStream {


    private val publisher = DirectProcessor.create<Event<in EventData, in EventMeta>>()

    private val events = ArrayList<Event<in EventData, in EventMeta>>()

    init {
        this.publisher.subscribe(this)
    }

    override fun publishEvent(event: Event<in EventData, in EventMeta>) {
        publisher.onNext(event)
    }

    override fun <T : EventData> streamEvents(type: KClass<T>): Flux<Event<T, in EventMeta>> {
        return Flux.merge(
                Flux.fromIterable(events),
                publisher
        ).filter { event -> event.data?.let {
            data -> data::class == type
        } ?: false } as Flux<Event<T, in EventMeta>>
    }

    override fun hookOnNext(event: Event<in EventData, in EventMeta>) {
        events.add(event)
    }

    override fun destroy() {
        publisher.onComplete()
        cancel()
    }

}