package mybooks

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.cloud.function.adapter.aws.SpringBootApiGatewayRequestHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.support.GenericMessage
import org.springframework.web.client.RestTemplate
import java.util.function.Function

@Configuration
class MyBooksAPIGateway {


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
    fun getAllBooksResource(): java.util.function.Function<Message<Any>, Message<String>> {
        return Function {
            println("+++++++++++++++++getAllBooks")
            GenericMessage("Hellowwwwww11111",
                    mapOf("Content-type" to "text/plain",
                            "statusCode" to 202))

        }
    }

}


class AddBookEventHandler : SpringBootApiGatewayRequestHandler(MyBooksApp::class.java) {
    override fun handleRequest(event: APIGatewayProxyRequestEvent?, context: Context?): Any {
        println("+++++++++++++++++AddBookEventHandler $event")
        System.setProperty("function.name", MyBooksAPIGateway::addBookResource.name)
        return super.handleRequest(event, context)
    }
}

class LoadBookEventHandler : SpringBootApiGatewayRequestHandler(MyBooksApp::class.java) {
    override fun handleRequest(event: APIGatewayProxyRequestEvent?, context: Context?): Any {
        println("+++++++++++++++++LoadBookEventHandler $event")
        System.setProperty("function.name", MyBooksAPIGateway::loadBookResource.name)
        return super.handleRequest(event, context)
    }
}

class GetAllBooksEventHandler : SpringBootApiGatewayRequestHandler(MyBooksApp::class.java) {
    override fun handleRequest(event: APIGatewayProxyRequestEvent?, context: Context?): Any {
        println("+++++++++++++++++GetAllBooksEventHandler ${event?.body}")
        System.setProperty("function.name", MyBooksAPIGateway::getAllBooksResource.name)
        return super.handleRequest(event, context)
    }
}