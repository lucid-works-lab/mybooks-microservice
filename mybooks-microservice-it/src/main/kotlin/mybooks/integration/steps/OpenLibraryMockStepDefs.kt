package mybooks.integration.steps

import com.github.tomakehurst.wiremock.client.WireMock
import mybooks.mocks.OpenLibraryMock
import org.springframework.beans.factory.annotation.Autowired

class OpenLibraryMockStepDefs : StepDefs {

    @Autowired
    lateinit var context: RestCallContext

    init {
        When("^running WireMock on host (\\S+) and port (\\S+)$") { host: String, port: Int ->
            WireMock.configureFor(host, port)
        }
        When("^stabbing OpenLibraryMock for stub (\\S+)$") { stubType: String ->
            OpenLibraryMock().mockFor(OpenLibraryMock.StubType.valueOf(stubType))
        }

    }


}
