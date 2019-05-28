package mybooks

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.messaging.support.GenericMessage
import org.springframework.web.client.RestTemplate
import java.time.YearMonth

@SpringBootApplication
class MyBooksApp {

    @Bean
    fun addBookResource(mapper: ObjectMapper): (APIGatewayProxyRequestEvent) -> APIGatewayProxyResponseEvent {
        return {
            println("+++++++++++++++++addBook  $it")
            APIGatewayProxyResponseEvent()
        }
    }

    @Bean
    fun loadBookResource(restTemplate: RestTemplate): (APIGatewayProxyRequestEvent) -> APIGatewayProxyResponseEvent {
        return {
            println("+++++++++++++++++loadBook $it")
            APIGatewayProxyResponseEvent()
        }
    }

    @Bean
    fun getAllBooksResource(): (Message<Any>) -> Message<String> {
        return {
            println("+++++++++++++++++getAllBooks")
            GenericMessage("Hellowwwwww11111",
                    mapOf("Content-type" to "text/plain",
                            "statusCode" to 202))

        }
    }

    @Bean
    fun getMapper(): ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

}

data class Book(val isbn: String, val title: String, val authors: List<String>, val published: YearMonth)

fun main(vararg args: String) {
    runApplication<MyBooksApp>(*args)
}
