package mybooks.eventbus

import reactor.core.publisher.Flux

interface EventService {

    fun publishEvent(event: Event<in EventData, in EventMeta>)

    fun streamEvents(): Flux<Event<in EventData, in EventMeta>>

}