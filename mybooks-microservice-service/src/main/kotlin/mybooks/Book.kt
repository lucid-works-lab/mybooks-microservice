package mybooks

import java.time.YearMonth
import javax.persistence.CollectionTable
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id

@Entity
data class Book(

        @Id
        val isbn: String,

        val title: String,

        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable
        val authors: List<String> = listOf(),

        val published: YearMonth
)