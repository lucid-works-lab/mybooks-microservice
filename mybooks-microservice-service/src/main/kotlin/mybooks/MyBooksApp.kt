package mybooks

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.util.ErrorHandler
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class MyBooksApp {

    @Bean
    fun objectMapper(): ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    @Qualifier("eventStream")
    fun eventStream(): InMemoryEventStream {
        return InMemoryEventStream()
    }

    @Bean
    @Qualifier("dlq")
    fun dlq(): InMemoryEventStream {
        return InMemoryEventStream()
    }
}

fun main(vararg args: String) {
    runApplication<MyBooksApp>(*args)
}
