package mybooks.eventbus.v1

import java.time.Instant
import mybooks.eventbus.EventMeta as EventMetaBase

data class EventMeta(

        var timestamp: Instant,

        var eventType: String,

        var dataVersion: String

) : EventMetaBase