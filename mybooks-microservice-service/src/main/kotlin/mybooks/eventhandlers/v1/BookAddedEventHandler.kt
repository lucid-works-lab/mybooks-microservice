package mybooks.eventhandlers.v1

import mybooks.Book
import mybooks.eventbus.Event
import mybooks.eventbus.data.v1.BookAdded
import mybooks.eventbus.meta.EventMeta
import mybooks.eventhandlers.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer

@Configuration
class BookAddedEventHandler : EventHandler<BookAdded>(BookAdded::class) {

    @Autowired
    @Qualifier("addBook")
    private lateinit var addBookFunction: Consumer<Book>

    override fun handleEvent(event: Event<BookAdded, out EventMeta>) {
        addBookFunction.accept(Book(
                isbn = event.data.isbn,
                title = event.data.title,
                authors = event.data.authors,
                published = event.data.published))
    }
}