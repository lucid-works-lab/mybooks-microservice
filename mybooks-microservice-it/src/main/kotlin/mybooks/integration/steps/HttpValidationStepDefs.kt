package mybooks.integration.steps

import io.cucumber.datatable.DataTable
import mybooks.integration.utils.JsonPathValidator
import mybooks.integration.utils.OpType
import mybooks.integration.utils.ValidationRule
import org.hamcrest.Matchers
import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired

class HttpValidationStepDefs : StepDefs {

    @Autowired
    lateinit var context: RestCallContext

    init {
        Given("^verifying status code is (\\d+)$") { statusCode: Int ->
            Assert.assertEquals(statusCode, context.statusCode)
        }
        Given("^verifying response body matches (.*)$") { regex: String ->
            Assert.assertThat(context.responseBody, Matchers.matchesPattern(regex))
        }
        Given("^verifying response body paths$") { table: DataTable ->
            val rules: List<ValidationRule> =
                    table.asMaps().map { list ->
                        ValidationRule(list["path"]
                                ?: throw Error("path can't be empty"), enumValues<OpType>().find { it.name == list["op"] }
                                ?: OpType.equal, list["value"])
                    }
            context.responseBody?.let { JsonPathValidator.assertJson(it, rules) }
        }
    }
}
