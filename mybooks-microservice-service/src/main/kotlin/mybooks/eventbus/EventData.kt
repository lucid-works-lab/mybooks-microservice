package mybooks.eventbus

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import mybooks.events.v1.BookAdded
import mybooks.events.v1.BookRemoved

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "command")
@JsonTypeIdResolver(EventDataTypeIdResolver::class)
interface EventData