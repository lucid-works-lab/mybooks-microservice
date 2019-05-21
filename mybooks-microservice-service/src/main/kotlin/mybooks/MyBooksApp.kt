package mybooks

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mybooks.domain.Book
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@SpringBootApplication
class MyBooksApp {


    private val books: MutableMap<String, Book> = mutableMapOf()

    @Bean
    fun addBook(mapper: ObjectMapper): (Map<String, Any>) -> Unit {
        return {
            val book = mapper.convertValue(it, Book::class.java)
            books[book.isbn] = book
        }
    }

    @Bean
    fun loadBook(restTemplate: RestTemplate): (String) -> Unit {
        return {
            val openLibBook: Map<String, Any>? =
                    restTemplate.getForObject("https://openlibrary.org/api/books?bibkeys=ISBN:$it&jscmd=data&format=json")
            val book = Book(
                    isbn = it,
                    title = (openLibBook?.get("ISBN:$it") as Map<String, Any>)["title"] as String,
                    authors = ((openLibBook["ISBN:$it"] as Map<String, Any>)["authors"] as List<Map<String, Any>>)
                            .map { publisher -> publisher["name"] as String },
                    published = YearMonth.parse((openLibBook["ISBN:$it"] as Map<String, Any>)["publish_date"] as String,
                            DateTimeFormatter.ofPattern("MMMM yyyy"))
            )
            books[book.isbn] = book
        }
    }

    @Bean
    fun getAllBooks(): () -> String {
        return {
            books.toString()
        }
    }

    @Bean
    fun getMapper(): ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()
}

fun main(vararg args: String) {
    runApplication<MyBooksApp>(*args)
}
