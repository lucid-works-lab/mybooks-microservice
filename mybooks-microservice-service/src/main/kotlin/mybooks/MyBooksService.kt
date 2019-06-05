package mybooks

import mybooks.eventbus.Event
import mybooks.eventbus.data.EventData
import mybooks.eventbus.meta.EventMeta
import mybooks.repo.BookRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier


@Configuration
class MyBooksService {

    @Bean
    @Qualifier("addBook")
    fun addBook(bookRepo: BookRepository): Consumer<Book> {
        return Consumer {
            bookRepo.save(it)
        }
    }

    @Bean
    @Qualifier("loadBook")
    fun loadBook(restTemplate: RestTemplate, bookRepo: BookRepository): Consumer<String> {
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
            bookRepo.save(book)
        }
    }

    @Bean
    @Qualifier("removeBook")
    fun removeBook(bookRepo: BookRepository): Consumer<String> {
        return Consumer {
            bookRepo.deleteById(UUID.fromString(it))
        }
    }

    @Bean
    @Qualifier("getBookByISBN")
    fun getBookByISBN(bookRepo: BookRepository): Function<String, Book?> {
        return Function {
            return@Function bookRepo.findByIsbn(it).last()
        }
    }

    @Bean
    @Qualifier("getAllBooks")
    fun getAllBooks(bookRepo: BookRepository): Supplier<List<Book>> {
        return Supplier {
            val list = arrayListOf<Book>()
            bookRepo.findAll().forEach { e -> list.add(e) }
            return@Supplier list
        }
    }

    @Bean
    @Qualifier("publishEvent")
    fun publishEvent(eventStream: MyBooksEventStream): Consumer<Event<in EventData, in EventMeta>> {
        return Consumer { event ->
            eventStream.publishEvent(event)
        }
    }
}