package mybooks.mocks

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.*

class OpenLibraryMock {

    enum class StubType {
        proxy, located, notLocated, badRequest, notFound, internalServerError
    }

    fun mockFor(type: StubType) {
        when(type) {
            StubType.proxy -> stubFor(get(urlMatching("^/books(.*)"))
                    .willReturn(aResponse()
                            .proxiedFrom("https://openlibrary.org/api")))
            StubType.located -> stubFor(get(urlMatching("^/books(.*)"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(OpenLibraryMock::class.java.getResource("/wiremock/body-book-located.json").readText())))
            StubType.notLocated -> stubFor(get(urlMatching("^/books(.*)"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{}")))
            StubType.badRequest -> stubFor(get(urlMatching("^/books(.*)"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withStatus(400)
                            .withBody("""{"error": "Bad Request"}""")))
            StubType.notFound -> stubFor(get(urlMatching("^/books(.*)"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withStatus(404)
                            .withBody("""{"error": "Not Found"}""")))
            StubType.internalServerError -> stubFor(get(urlMatching("^/books(.*)"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withStatus(500)
                            .withBody("""{"error": "Internal Server Error"}""")))
        }

    }
}

fun main() {
    WireMock.configureFor("localhost", 9090)
    WireMock.removeAllMappings()
    OpenLibraryMock().mockFor(OpenLibraryMock.StubType.located)
}