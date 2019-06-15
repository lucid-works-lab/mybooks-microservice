Feature: My Books regression tests

  Scenario Outline: Call addBook - <description>
    When calling addBook endpoint <endpoint>
    Then verifying status code is 202
    And verifying response body paths
      | path     | value      |
      | $.*.isbn | 0060934344 |

  @local
    Examples:
      | description       | endpoint              |
      | positive scenario | http://localhost:8080 |

  Scenario Outline: Call loadBook - <description>
    When running WireMock on host localhost and port 9090
    And stabbing OpenLibraryMock for stub <stubType>
    When calling loadBook endpoint <endpoint>
    Then verifying status code is <statusCode>
    And verifying response body matches <bodyRegex>

  @local
    Examples:
      | description                               | endpoint              | stubType            | statusCode | bodyRegex                                                                                                                                                                                   |
      | positive scenario                         | http://localhost:8080 | located             | 202        |                                                                                                                                                                                             |
      | openlibrary returns Bad Request           | http://localhost:8080 | badRequest          | 500        | .+\"status\":500,\"error\":\"reactor.core.Exceptions.ErrorCallbackNotImplemented\",\"httpStatus\":\"INTERNAL_SERVER_ERROR\",\"message\":\"mybooks.exceptions.OpenLibraryClientException\".+ |
      | openlibrary returns Not Found             | http://localhost:8080 | notFound            | 500        | .+\"status\":500,\"error\":\"reactor.core.Exceptions.ErrorCallbackNotImplemented\",\"httpStatus\":\"INTERNAL_SERVER_ERROR\",\"message\":\"mybooks.exceptions.OpenLibraryClientException\".+ |
      | openlibrary returns Internal Server Error | http://localhost:8080 | internalServerError | 503        | .+\"status\":503,\"error\":\"reactor.core.Exceptions.ErrorCallbackNotImplemented\",\"httpStatus\":\"SERVICE_UNAVAILABLE\",\"message\":\"mybooks.exceptions.OpenLibraryServerException\".+   |





