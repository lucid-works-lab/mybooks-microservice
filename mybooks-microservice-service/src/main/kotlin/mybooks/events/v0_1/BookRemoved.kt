package mybooks.events.v0_1

import mybooks.eventbus.EventData
import java.time.YearMonth

data class BookRemoved(
        val isbn: String
):EventData