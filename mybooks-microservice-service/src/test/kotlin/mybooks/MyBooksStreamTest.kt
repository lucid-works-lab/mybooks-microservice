package mybooks

import mybooks.eventbus.Event
import mybooks.eventbus.data.v1.BookAdded
import mybooks.eventbus.meta.v1.EventMeta
import org.junit.Assert
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.junit4.SpringRunner
import java.time.Instant
import java.time.YearMonth
import java.util.*
import java.util.function.Supplier

@Tag("unit")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [MyBooksApp::class],
        webEnvironment = RANDOM_PORT)
class MyBooksStreamTest {

    @Autowired
    lateinit var eventStream: InMemoryEventStream

    @Autowired
    @Qualifier("getAllBooks")
    lateinit var getAllBooks: Supplier<List<Book>>

    @Test
    fun saveAndRetrieveBook() {
        val bookAddedEventData = BookAdded(isbn = "0060934344",
                title = "Don Quixote",
                authors = listOf("Miguel de Cervantes"),
                published = YearMonth.of(2005, 4))
        eventStream.publishEvent(Event(id = UUID.fromString("9aef1f7e-90b1-11e9-bc42-526af7764f64"), meta = EventMeta(timestamp = Instant.now()), data = bookAddedEventData))
        val book = Book(isbn = "0060934344",
                title = "Don Quixote",
                authors = listOf("Miguel de Cervantes"),
                published = YearMonth.of(2005, 4))

        Assert.assertEquals("Retrievd Book is not the same as saved one", book, getAllBooks.get().get(0))
    }

}