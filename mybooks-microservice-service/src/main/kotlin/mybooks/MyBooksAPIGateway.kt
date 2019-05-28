package mybooks

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import org.springframework.cloud.function.adapter.aws.SpringBootApiGatewayRequestHandler
import org.springframework.stereotype.Component

@Component
class AddBookEventHandler : SpringBootApiGatewayRequestHandler(MyBooksApp::class.java) {
    override fun handleRequest(event: APIGatewayProxyRequestEvent?, context: Context?): Any {
        println("+++++++++++++++++AddBookEventHandler $event")
        System.setProperty("function.name", MyBooksApp::addBookResource.name)
        return super.handleRequest(event, context)
    }
}

@Component
class LoadBookEventHandler : SpringBootApiGatewayRequestHandler(MyBooksApp::class.java) {
    override fun handleRequest(event: APIGatewayProxyRequestEvent?, context: Context?): Any {
        println("+++++++++++++++++LoadBookEventHandler $event")
        System.setProperty("function.name", MyBooksApp::loadBookResource.name)
        return super.handleRequest(event, context)
    }
}

@Component
class GetAllBooksEventHandler : SpringBootApiGatewayRequestHandler(MyBooksApp::class.java) {
    override fun handleRequest(event: APIGatewayProxyRequestEvent?, context: Context?): Any {
        println("+++++++++++++++++GetAllBooksEventHandler ${event?.body}")
        System.setProperty("function.name", MyBooksApp::getAllBooksResource.name)
        return super.handleRequest(event, context)
    }
}