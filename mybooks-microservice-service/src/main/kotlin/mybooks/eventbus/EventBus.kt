package mybooks.eventbus

import reactor.core.publisher.Flux

interface EventBus {

    fun publishEvent(event: Event<in EventData, in EventMeta>)

    fun streamEvents(): Flux<Event<in EventData, in EventMeta>>

}