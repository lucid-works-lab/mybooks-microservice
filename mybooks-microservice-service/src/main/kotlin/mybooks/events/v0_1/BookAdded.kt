package mybooks.events.v0_1

import mybooks.eventbus.EventData
import java.time.YearMonth

data class BookAdded(
        val isbn: String,
        val title: String,
        val authors: List<String>,
        val published: YearMonth
):EventData