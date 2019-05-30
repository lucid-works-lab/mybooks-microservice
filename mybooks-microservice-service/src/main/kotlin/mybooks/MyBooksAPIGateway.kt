package mybooks

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
            println("+++++++++++++++++loadBook $it")
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
            println("+++++++++++++++++getAllBooks")
            GenericMessage("", //mapper.writeValueAsString(function.apply())
                    mapOf("Content-type" to "application/json",
                            "statusCode" to 202))
        }
    }

}

class AddBookEventHandler : SpringBootApiGatewayRequestHandler(MyBooksApp::class.java) {
    override fun handleRequest(event: APIGatewayProxyRequestEvent?, context: Context?): Any {
        System.setProperty("function.name", MyBooksAPIGateway::addBookResource.name)
        return super.handleRequest(event, context)
    }
}

class LoadBookEventHandler : SpringBootApiGatewayRequestHandler(MyBooksApp::class.java) {
    override fun handleRequest(event: APIGatewayProxyRequestEvent?, context: Context?): Any {
        System.setProperty("function.name", MyBooksAPIGateway::loadBookResource.name)
        return super.handleRequest(event, context)
    }
}

class GetAllBooksEventHandler : SpringBootApiGatewayRequestHandler(MyBooksApp::class.java) {
    override fun handleRequest(event: APIGatewayProxyRequestEvent?, context: Context?): Any {
        System.setProperty("function.name", MyBooksAPIGateway::getAllBooksResource.name)
        return super.handleRequest(event, context)
    }
}