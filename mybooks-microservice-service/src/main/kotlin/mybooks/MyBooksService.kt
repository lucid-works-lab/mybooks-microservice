package mybooks

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.function.Consumer
import java.util.function.Supplier
import java.util.function.Function

@Configuration
class MyBooksService {

    private val books: MutableMap<String, Book> =
            mutableMapOf("abc" to Book("abc", "The Book II", listOf(), YearMonth.of(2000, 1)))

    @Bean
    @Qualifier("addBook")
    fun addBook(): Consumer<Book> {
        return Consumer {
            books[it.isbn] = it
        }
    }

    @Bean
    @Qualifier("loadBook")
    fun loadBook(restTemplate: RestTemplate): Consumer<String> {
        return Consumer {
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
    @Qualifier("removeBook")
    fun removeBook(): Consumer<String> {
        return Consumer {
            books.remove(it)
        }
    }

    @Bean
    @Qualifier("getBookByISBN")
    fun getBookByISBN(): Function<String, Book?> {
        return Function {
            books[it]
        }
    }

    @Bean
    @Qualifier("getAllBooks")
    fun getAllBooks(): Supplier<String> {
        return Supplier {
            books.toString()
        }
    }

}