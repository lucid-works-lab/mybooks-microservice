package mybooks.events.v1

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
class EventDispatcher : CommandLineRunner {

    @Autowired
    private lateinit var eventBus: EventBus

    override fun run(vararg args: String?) {
        eventBus.register(this)
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
