package mybooks.eventhandlers

import mybooks.InMemoryEventStream
import mybooks.eventbus.Event
import mybooks.eventbus.EventStream
import mybooks.eventbus.data.EventData
import mybooks.eventbus.meta.EventMeta
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class EventHandlerManager {

    @Autowired
    @Qualifier("eventStream")
    private lateinit var eventStream: EventStream


    @Autowired
    @Qualifier("dlq")
    private lateinit var dlq: EventStream

    fun <T : EventData> addEventHandler(type: KClass<T>, handler: (Event<T, out EventMeta>) -> Unit) {
        eventStream.streamEvents(type).subscribe { event ->
            handler(event)
        }
    }

}
