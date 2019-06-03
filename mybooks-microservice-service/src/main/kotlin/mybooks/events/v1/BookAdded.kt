package mybooks.events.v1

import com.fasterxml.jackson.annotation.JsonTypeName
import mybooks.eventbus.EventData
import java.time.YearMonth

@JsonTypeName("BookAdded_v1")
data class BookAdded(
        val isbn: String,
        val title: String,
        val authors: List<String>,
        val published: YearMonth
) : EventData
