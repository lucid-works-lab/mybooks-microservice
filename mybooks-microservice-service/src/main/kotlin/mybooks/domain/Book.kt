package mybooks.domain

import java.time.YearMonth

data class Book(val isbn: String, val title: String, val authors: List<String>, val published: YearMonth)