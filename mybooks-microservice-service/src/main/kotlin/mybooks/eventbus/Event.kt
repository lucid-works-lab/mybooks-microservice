package mybooks.eventbus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
interface Event<TEventData : EventData, TEventMeta : EventMeta> {

    var meta: TEventMeta?

    var metaVersion: String?

    var data: TEventData?


}
