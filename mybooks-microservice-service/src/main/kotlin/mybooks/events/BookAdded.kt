package mybooks.events

import mybooks.eventbus.EventData
import java.time.YearMonth

data class BookAdded(
        val isbn: String,
        val title: String,
        val authors: List<String>,
        val published: YearMonth
):EventData