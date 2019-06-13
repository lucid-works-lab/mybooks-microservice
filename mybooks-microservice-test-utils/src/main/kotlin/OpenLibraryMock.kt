import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.*

class OpenLibraryMock {

    enum class Mapping {
        located, notLocated, badRequest, notFound, internalServerError
    }

    fun mockFor(mapping:Mapping) {
        when(mapping) {
            Mapping.located -> stubFor(get(urlMatching("^/wiremock/openlibrary/books(.*)"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(OpenLibraryMock::class.java.getResource("/wiremock/body-book-located.json").readText())))
            Mapping.notLocated -> stubFor(get(urlMatching("^/wiremock/openlibrary/books(.*)"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{}")))
            Mapping.badRequest -> stubFor(get(urlMatching("^/wiremock/openlibrary/books(.*)"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("""{"text": "Bad Request"}""")))
            Mapping.notFound -> stubFor(get(urlMatching("^/wiremock/openlibrary/books(.*)"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("""{"text": "Not Found"}""")))
            Mapping.internalServerError -> stubFor(get(urlMatching("^/wiremock/openlibrary/books(.*)"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("""{"text": "Internal Server Error"}""")))
        }

    }
}

fun main() {
    WireMock.configureFor("localhost", 9090)
    WireMock.removeAllMappings()
    OpenLibraryMock().mockFor(OpenLibraryMock.Mapping.located)
}