package mybooks.eventbus

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import mybooks.events.v1.BookAdded
import mybooks.events.v1.BookRemoved

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(
        JsonSubTypes.Type(value = BookAdded::class, name = "BookAdded_v1"),
        JsonSubTypes.Type(value = BookRemoved::class, name = "BookRemoved_v1"))
interface EventData