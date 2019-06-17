package mybooks

import org.junit.Assert
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.junit4.SpringRunner
import java.time.YearMonth
import java.util.function.Consumer
import java.util.function.Supplier

@Tag("unit")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [MyBooksApp::class],
        webEnvironment = RANDOM_PORT)
class MyBooksFunctionTest {

    @Autowired
    @Qualifier("addBook")
    lateinit var addBook: Consumer<Book>

    @Autowired
    @Qualifier("getAllBooks")
    lateinit var getAllBooks: Supplier<List<Book>>


    @Test
    fun saveAndRetrieveBook() {
        val book =  Book(isbn = "0060934344",
                title = "Don Quixote",
                authors = listOf("Miguel de Cervantes"),
                published = YearMonth.of(2005, 4))
        addBook.accept(book)
        Assert.assertEquals("Retrievd Book is not the same as saved one", book, getAllBooks.get().get(0))
    }
}