package mybooks.repo

import mybooks.Book
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<Book, String>