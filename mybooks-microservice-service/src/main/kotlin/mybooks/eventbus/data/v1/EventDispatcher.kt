package mybooks.eventbus.data.v1

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import mybooks.Book
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
    private lateinit var eventBus: EventBus

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    @Qualifier("addBook")
    private lateinit var addBookFunction: Consumer<Book>

    @Autowired
    @Qualifier("removeBook")
    private lateinit var removeBookFunction: Consumer<String>


    override fun run(vararg args: String?) {
        eventBus.register(this)
    }


    @Subscribe
    fun apply(event: BookAdded) {
        addBookFunction.accept(Book(
                isbn = event.isbn,
                title  = event.title,
                authors = event.authors,
                published = event.published))
    }

    @Subscribe
    fun apply(event: BookRemoved) {
        removeBookFunction.accept(event.isbn)
    }
}
