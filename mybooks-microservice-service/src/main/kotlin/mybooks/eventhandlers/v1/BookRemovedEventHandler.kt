package mybooks.eventhandlers.v1

import mybooks.eventbus.Event
import mybooks.eventbus.data.v1.BookRemoved
import mybooks.eventbus.meta.EventMeta
import mybooks.eventhandlers.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer

@Configuration
class BookRemovedEventHandler : EventHandler<BookRemoved>(BookRemoved::class) {

    @Autowired
    @Qualifier("removeBook")
    private lateinit var removeBookFunction: Consumer<String>

   override fun handleEvent(event: Event<BookRemoved, out EventMeta>) {
        removeBookFunction.accept(event.data.isbn)
    }
}