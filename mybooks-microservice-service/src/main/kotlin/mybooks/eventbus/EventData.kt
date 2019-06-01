package mybooks.eventbus

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import mybooks.events.v0_1.BookAdded
import mybooks.events.v0_1.BookRemoved

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(
        JsonSubTypes.Type(value = BookAdded::class, name = "BookAdded_v0_1"),
        JsonSubTypes.Type(value = BookRemoved::class, name = "BookRemoved_v0_1"))
interface EventData