package mybooks.integration.steps

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import mybooks.mocks.OpenLibraryMock
import org.springframework.beans.factory.annotation.Autowired

class OpenLibraryStepDefs : StepDefs {

    @Autowired
    lateinit var context: RestCallContext

    init {
        When("^running WireMock on host (\\S+) and port (\\d+)$") { host: String, port: Int ->

            WireMock.configureFor(host, port)

            try {
                val wireMockServer = WireMockServer(options().port(port))
                wireMockServer.start()
            } catch (e:Exception) {
                println("Wiremock is probably already running on host $host and port $port")
            }

        }
        When("^stabbing OpenLibraryMock for stub (\\S+)$") { stubType: String ->
            OpenLibraryMock().mockFor(OpenLibraryMock.StubType.valueOf(stubType))
        }

    }


}
