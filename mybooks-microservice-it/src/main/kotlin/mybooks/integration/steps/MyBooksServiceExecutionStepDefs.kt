package mybooks.integration.steps

import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.springframework.beans.factory.annotation.Autowired

class MyBooksServiceExecutionStepDefs : StepDefs {

    @Autowired
    lateinit var context: RestCallContext

    init {
        When("^calling addBook endpoint (\\S+)$") { endpoint: String ->
            val request = HttpPost("$endpoint/addBook")
            request.entity = StringEntity("""
{
    "isbn": "asd",
    "title": "The Book",
    "authors": ["Abc", "Def"],
    "published": "2012-01"
}
            """.trimIndent(),
                    ContentType.APPLICATION_JSON)
            request.addHeader("accept", "application/json")
            val httpClient = HttpClients.createDefault()
            val response = httpClient.execute(request)
            context.responseBody = EntityUtils.toString(response.entity)
            context.statusCode = response.statusLine.statusCode
        }
    }


}
