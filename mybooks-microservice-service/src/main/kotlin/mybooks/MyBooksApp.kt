package mybooks

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import java.time.YearMonth

@SpringBootApplication
class MyBooksApp {

    @Bean
    fun getMapper(): ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

}

data class Book(val isbn: String, val title: String, val authors: List<String>, val published: YearMonth)

fun main(vararg args: String) {
    runApplication<MyBooksApp>(*args)
}
