package mybooks.eventhandlers

import mybooks.eventbus.Event
import mybooks.eventbus.data.EventData
import mybooks.eventbus.meta.EventMeta
import org.springframework.beans.factory.annotation.Autowired

abstract class EventHandler {

    @Autowired
    lateinit var eventHandlerManager: EventHandlerManager

    inline fun <reified T : EventData> addEventHandler(noinline handler: (Event<T, out EventMeta>) -> Unit) =
            eventHandlerManager.addEventHandler(T::class, handler)
}