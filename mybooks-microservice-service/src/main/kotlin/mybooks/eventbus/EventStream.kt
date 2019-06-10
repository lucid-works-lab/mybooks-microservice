package mybooks.eventbus

import mybooks.eventbus.data.EventData
import mybooks.eventbus.meta.EventMeta
import reactor.core.publisher.Flux
import kotlin.reflect.KClass

interface EventStream {

    fun publishEvent(event: Event<in EventData, in EventMeta>)

    fun <T : EventData> streamEvents(type: KClass<T>): Flux<Event<T, in EventMeta>>

}