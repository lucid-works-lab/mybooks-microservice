package mybooks.eventbus.data.v1

import com.fasterxml.jackson.annotation.JsonTypeName
import mybooks.eventbus.data.EventData

@JsonTypeName("BookRemoved_v1")
data class BookRemoved(
        val isbn: String
): EventData