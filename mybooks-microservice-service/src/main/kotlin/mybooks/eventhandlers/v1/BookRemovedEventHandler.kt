package mybooks.eventhandlers.v1

import mybooks.eventbus.data.v1.BookRemoved
import mybooks.eventhandlers.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.function.Consumer
import javax.annotation.PostConstruct

@Component
class BookRemovedEventHandler : EventHandler() {

    @Autowired
    @Qualifier("removeBook")
    lateinit var removeBookFunction: Consumer<String>

    @PostConstruct
    fun init() {
        addEventHandler<BookRemoved> { event ->
            removeBookFunction.accept(event.data.isbn)
        }
    }
}