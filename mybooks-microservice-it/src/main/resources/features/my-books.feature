Feature: My Books regression tests

  Scenario Outline: Call addBook
    When calling addBook endpoint <endpoint>
    Then verifying status code is 202

  @local
    Examples:
      | endpoint              |
      | http://localhost:8080 |

  Scenario Outline: Call loadBook - <description>
    When running WireMock on host localhost and port 9090
    And stabbing OpenLibraryMock for stub <stubType>
    When calling loadBook endpoint <endpoint>
    Then verifying status code is <statusCode>

  @local
    Examples:
      | description                               | endpoint              | stubType            | statusCode |
      | positive scenario                         | http://localhost:8080 | located             | 202        |
      | openlibrary returns Bad Request           | http://localhost:8080 | badRequest          | 500        |
      | openlibrary returns Not Found             | http://localhost:8080 | notFound            | 500        |
      | openlibrary returns Internal Server Error | http://localhost:8080 | internalServerError | 503        |






