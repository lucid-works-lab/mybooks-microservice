package mybooks.eventbus.v1

import com.fasterxml.jackson.annotation.JsonTypeName
import java.time.Instant
import mybooks.eventbus.EventMeta as EventMetaBase

@JsonTypeName("v1")
data class EventMeta(

        var timestamp: Instant

) : EventMetaBase