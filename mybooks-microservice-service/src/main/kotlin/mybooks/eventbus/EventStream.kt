package mybooks.eventbus

import mybooks.eventbus.data.EventData
import mybooks.eventbus.meta.EventMeta
import reactor.core.publisher.Flux

interface EventStream {

    fun publishEvent(event: Event<in EventData, in EventMeta>)

    fun streamEvents(): Flux<Event<in EventData, in EventMeta>>

}