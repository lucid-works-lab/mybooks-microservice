package mybooks.events.v1

import mybooks.eventbus.EventData

data class BookRemoved(
        val isbn: String
):EventData