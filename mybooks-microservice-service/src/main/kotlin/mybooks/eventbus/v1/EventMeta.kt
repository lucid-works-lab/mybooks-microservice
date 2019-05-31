package mybooks.eventbus.v1

import java.time.Instant
import java.util.*
import mybooks.eventbus.EventMeta as EventMetaBase

data class EventMeta(

        var id: UUID,

        var timestamp: Instant,

        var eventType: String,

        var dataVersion: String

) : EventMetaBase