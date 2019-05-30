package mybooks

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.function.adapter.aws.SpringBootApiGatewayRequestHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.support.GenericMessage
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier

@Configuration
class MyBooksAPIGateway {

    @Bean
    fun addBookResource(@Qualifier("addBook") function: Consumer<Book>,
                        mapper: ObjectMapper): Function<Message<Any>, Message<String>> {
        return Function {
            function.accept(mapper.convertValue(it.payload, Book::class.java))
            GenericMessage("Success!",
                    mapOf("Content-type" to "application/json",
                            "statusCode" to 202))
        }
    }

    @Bean
    fun loadBookResource(@Qualifier("loadBook") function: Consumer<String>,
                         mapper: ObjectMapper): Function<Message<Any>, Message<String>> {
        return Function {
            function.accept(it.payload as String)
            GenericMessage("Success!",
                    mapOf("Content-type" to "application/json",
                            "statusCode" to 202))
        }
    }

    @Bean
    fun getAllBooksResource(@Qualifier("getAllBooks") function: Supplier<String>,
                            mapper: ObjectMapper): Function<Message<Any>, Message<String>> {
        return Function {
            GenericMessage(mapper.writeValueAsString(function.get()),
                    mapOf("Content-type" to "application/json",
                            "statusCode" to 202))
        }
    }

}

class Handler : SpringBootApiGatewayRequestHandler(MyBooksApp::class.java)