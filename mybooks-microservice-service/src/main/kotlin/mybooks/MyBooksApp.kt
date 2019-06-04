package mybooks

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.common.eventbus.EventBus
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class MyBooksApp {

    @Bean
    fun objectMapper(): ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    fun eventBus(): EventBus = EventBus()

}

fun main(vararg args: String) {
    runApplication<MyBooksApp>(*args)
}
