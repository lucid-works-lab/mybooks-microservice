package mybooks.eventbus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mybooks.eventbus.data.EventData
import mybooks.eventbus.meta.EventMeta
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Event<TEventData : EventData, TEventMeta : EventMeta> (

    var id: UUID,

    var meta: TEventMeta,

    var data: TEventData

)
