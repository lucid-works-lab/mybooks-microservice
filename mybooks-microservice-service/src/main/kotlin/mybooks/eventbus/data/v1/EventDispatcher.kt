package mybooks.eventbus.data.v1

import mybooks.Book
import mybooks.MyBooksEventStream
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import java.util.function.Consumer

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
class EventDispatcher : CommandLineRunner {

    @Autowired
    private lateinit var eventStream: MyBooksEventStream

    @Autowired
    @Qualifier("addBook")
    private lateinit var addBookFunction: Consumer<Book>

    @Autowired
    @Qualifier("removeBook")
    private lateinit var removeBookFunction: Consumer<String>

    override fun run(vararg args: String?) {
        eventStream.streamEvents().subscribe { event ->
            event.data?.let { data ->
                when (data) {
                    is BookAdded -> {
                        addBookFunction.accept(Book(
                                isbn = data.isbn,
                                title = data.title,
                                authors = data.authors,
                                published = data.published))
                    }
                    is BookRemoved -> removeBookFunction.accept(data.isbn)
                }
            }
        }
    }

}
