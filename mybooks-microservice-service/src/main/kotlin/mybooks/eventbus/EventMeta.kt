package mybooks.eventbus

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import mybooks.eventbus.v1.EventMeta

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "metaVersion")
@JsonTypeIdResolver(EventMetaTypeIdResolver::class)
@JsonSubTypes(
        JsonSubTypes.Type(value = EventMeta::class, name = "v1"))
interface EventMeta