package mybooks.eventhandlers

import mybooks.InMemoryEventStream
import mybooks.eventbus.Event
import mybooks.eventbus.data.EventData
import mybooks.eventbus.meta.EventMeta
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import kotlin.reflect.KClass

abstract class EventHandler<T:EventData>(private val type: KClass<T>) : CommandLineRunner {

    @Autowired
    private lateinit var eventStream: InMemoryEventStream

    override fun run(vararg args: String?) {
        eventStream.streamEvents(type).subscribe { event ->
            handleEvent(event)
        }
    }

    abstract fun handleEvent(event: Event<T, out EventMeta>)
}
