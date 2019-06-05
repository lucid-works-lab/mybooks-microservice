package mybooks.eventbus.meta

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import mybooks.eventbus.EventTypeIdResolver

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "metaVersion")
@JsonTypeIdResolver(EventTypeIdResolver::class)
interface EventMeta