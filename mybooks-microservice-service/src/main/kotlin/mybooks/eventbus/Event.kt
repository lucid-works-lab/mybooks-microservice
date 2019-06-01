package mybooks.eventbus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Event<TEventData : EventData, TEventMeta : EventMeta> (

    var id: UUID,

    var meta: TEventMeta,

    var metaVersion: String,

    var data: TEventData

)
