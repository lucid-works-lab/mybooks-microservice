Feature: My Books regression tests

  Scenario Outline: Call addBook - <description>
    When calling addBook endpoint <endpoint>
    Then verifying status code is 202
    When calling getBookByISBN endpoint <endpoint> with ISBN 0060934344
    And verifying response body paths
      | path   | value      |
      | $.isbn | 0060934344 |

  @local
    Examples:
      | description       | endpoint              |
      | positive scenario | http://localhost:8080 |

  @dev
    Examples:
      | description       | endpoint                                     |
      | positive scenario | http://mybooks.digitalocean.lucid.works:8080 |

  Scenario Outline: Call loadBook - <description>
    When running WireMock on host <wiremockHost> and port <wiremockPort>
    And stabbing OpenLibraryMock for stub <stubType>
    When calling loadBook endpoint <endpoint> with ISBN 9780980200447
    Then verifying status code is <statusCode>
    And verifying response body matches <bodyRegex>

  @local
    Examples:
      | description                                       | wiremockHost | wiremockPort | endpoint              | stubType            | statusCode | bodyRegex                                                                                                                                                                                                               |
      | positive scenario                                 | localhost    | 9090         | http://localhost:8080 | proxy               | 202        |                                                                                                                                                                                                                         |
      | positive scenario                                 | localhost    | 9090         | http://localhost:8080 | located             | 202        |                                                                                                                                                                                                                         |
      | openlibrary returns empty result                  | localhost    | 9090         | http://localhost:8080 | notLocated          | 404        | .+\"status\":404,\"error\":\"reactor.core.Exceptions.ErrorCallbackNotImplemented\",\"httpStatus\":\"NOT_FOUND\",\"message\":\"mybooks.exceptions.OpenLibraryNotFoundException: No book found for ISBN 9780980200447\".+ |
      | openlibrary returns Error - Bad Request           | localhost    | 9090         | http://localhost:8080 | badRequest          | 500        | .+\"status\":500,\"error\":\"reactor.core.Exceptions.ErrorCallbackNotImplemented\",\"httpStatus\":\"INTERNAL_SERVER_ERROR\",\"message\":\"mybooks.exceptions.OpenLibraryClientException\".+                             |
      | openlibrary returns Error - Not Found             | localhost    | 9090         | http://localhost:8080 | notFound            | 500        | .+\"status\":500,\"error\":\"reactor.core.Exceptions.ErrorCallbackNotImplemented\",\"httpStatus\":\"INTERNAL_SERVER_ERROR\",\"message\":\"mybooks.exceptions.OpenLibraryClientException\".+                             |
      | openlibrary returns Error - Internal Server Error | localhost    | 9090         | http://localhost:8080 | internalServerError | 503        | .+\"status\":503,\"error\":\"reactor.core.Exceptions.ErrorCallbackNotImplemented\",\"httpStatus\":\"SERVICE_UNAVAILABLE\",\"message\":\"mybooks.exceptions.OpenLibraryServerException\".+                               |

  @dev
    Examples:
      | description                                       | wiremockHost                     | wiremockPort | endpoint                                     | stubType            | statusCode | bodyRegex                                                                                                                                                                                                               |
      | positive scenario                                 | mybooks.digitalocean.lucid.works | 9090         | http://mybooks.digitalocean.lucid.works:8080 | proxy               | 202        |                                                                                                                                                                                                                         |
      | positive scenario                                 | mybooks.digitalocean.lucid.works | 9090         | http://mybooks.digitalocean.lucid.works:8080 | located             | 202        |                                                                                                                                                                                                                         |
      | openlibrary returns empty result                  | mybooks.digitalocean.lucid.works | 9090         | http://mybooks.digitalocean.lucid.works:8080 | notLocated          | 404        | .+\"status\":404,\"error\":\"reactor.core.Exceptions.ErrorCallbackNotImplemented\",\"httpStatus\":\"NOT_FOUND\",\"message\":\"mybooks.exceptions.OpenLibraryNotFoundException: No book found for ISBN 9780980200447\".+ |
      | openlibrary returns Error - Bad Request           | mybooks.digitalocean.lucid.works | 9090         | http://mybooks.digitalocean.lucid.works:8080 | badRequest          | 500        | .+\"status\":500,\"error\":\"reactor.core.Exceptions.ErrorCallbackNotImplemented\",\"httpStatus\":\"INTERNAL_SERVER_ERROR\",\"message\":\"mybooks.exceptions.OpenLibraryClientException\".+                             |
      | openlibrary returns Error - Not Found             | mybooks.digitalocean.lucid.works | 9090         | http://mybooks.digitalocean.lucid.works:8080 | notFound            | 500        | .+\"status\":500,\"error\":\"reactor.core.Exceptions.ErrorCallbackNotImplemented\",\"httpStatus\":\"INTERNAL_SERVER_ERROR\",\"message\":\"mybooks.exceptions.OpenLibraryClientException\".+                             |
      | openlibrary returns Error - Internal Server Error | mybooks.digitalocean.lucid.works | 9090         | http://mybooks.digitalocean.lucid.works:8080 | internalServerError | 503        | .+\"status\":503,\"error\":\"reactor.core.Exceptions.ErrorCallbackNotImplemented\",\"httpStatus\":\"SERVICE_UNAVAILABLE\",\"message\":\"mybooks.exceptions.OpenLibraryServerException\".+                               |




