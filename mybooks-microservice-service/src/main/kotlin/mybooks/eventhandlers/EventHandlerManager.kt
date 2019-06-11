package mybooks.eventhandlers

import mybooks.InMemoryEventStream
import mybooks.eventbus.Event
import mybooks.eventbus.data.EventData
import mybooks.eventbus.meta.EventMeta
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class EventHandlerManager {

    @Autowired
    private lateinit var eventStream: InMemoryEventStream

    fun <T : EventData> addEventHandler(type: KClass<T>, handler: (Event<T, out EventMeta>) -> Unit) {
        eventStream.streamEvents(type).subscribe { event ->
            handler(event)
        }
    }

}
