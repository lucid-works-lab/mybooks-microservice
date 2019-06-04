package mybooks.repo

import mybooks.Book
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BookRepository : CrudRepository<Book, UUID> {

    fun findByIsbn(isbn: String): List<Book>
}