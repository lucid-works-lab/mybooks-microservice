package mybooks

import com.google.common.eventbus.EventBus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order

@Configuration
class MyBooksEventHandler : CommandLineRunner {

    @Autowired
    private lateinit var eventStream: MyBooksEventStream

    @Autowired
    private lateinit var eventBus: EventBus

    override fun run(vararg args: String?) {
        eventStream.streamEvents().subscribe {
            it.data?.let { data -> eventBus.post(data) }
        }
    }

}