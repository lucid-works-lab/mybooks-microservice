package mybooks

import com.google.common.eventbus.EventBus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import com.google.common.eventbus.Subscribe
import mybooks.events.v1.BookAdded
import mybooks.events.v1.BookRemoved


@Configuration
class MyBooksEventHandler : CommandLineRunner {

    @Autowired
    private lateinit var eventStream: MyBooksEventStream

    @Autowired
    private lateinit var eventBus: EventBus

    override fun run(vararg args: String?) {
        eventBus.register(this)
        eventStream.streamEvents().subscribe {
            it.data?.let {data -> eventBus.post(data)}
        }
    }

    @Subscribe
    fun apply(event: BookAdded) {
        println("++++++++++++++ ${event}")
    }

    @Subscribe
    fun apply(event: BookRemoved) {
        println("++++++++++++++ ${event}")
    }
}