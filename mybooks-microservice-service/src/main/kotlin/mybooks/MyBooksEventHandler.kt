package mybooks

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration

@Configuration
class MyBooksEventHandler : CommandLineRunner {

    @Autowired
    private lateinit var eventBus: MyBooksEventBus

    override fun run(vararg args: String?) {
        eventBus.streamEvents().subscribe {
            println("++++++++++++++ ${it.data}")
        }
    }
}