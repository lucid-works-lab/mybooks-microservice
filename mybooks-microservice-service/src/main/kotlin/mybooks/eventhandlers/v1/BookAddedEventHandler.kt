package mybooks.eventhandlers.v1

import mybooks.Book
import mybooks.eventbus.data.v1.BookAdded
import mybooks.eventhandlers.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.function.Consumer
import javax.annotation.PostConstruct

@Component
class BookAddedEventHandler : EventHandler() {

    @Autowired
    @Qualifier("addBook")
    lateinit var addBookFunction: Consumer<Book>

    @PostConstruct
    fun init() {
        addEventHandler<BookAdded> { event ->
            addBookFunction.accept(Book(
                    isbn = event.data.isbn,
                    title = event.data.title,
                    authors = event.data.authors,
                    published = event.data.published))
        }
    }
}