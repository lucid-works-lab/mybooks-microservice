package mybooks.events.v1

import com.fasterxml.jackson.annotation.JsonTypeName
import mybooks.eventbus.EventData

@JsonTypeName("BookRemoved_v1")
data class BookRemoved(
        val isbn: String
):EventData