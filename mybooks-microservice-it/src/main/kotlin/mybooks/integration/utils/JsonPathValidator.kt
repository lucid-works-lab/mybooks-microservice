package mybooks.integration.utils

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import net.minidev.json.JSONArray
import org.hamcrest.Matchers
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import org.junit.Assert
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.script.ScriptEngineManager
import javax.script.ScriptException

@Suppress("TooGenericExceptionThrown")
object JsonPathValidator {

    init {
        setIdeaIoUseFallback()
    }

    val scriptEngine = ScriptEngineManager().getEngineByExtension("kts").also {
        it.eval("import com.loyalty.nova.common.test.utils.JsonPathValidator.getShiftedStartDate")
        it.eval("import com.loyalty.nova.common.test.utils.JsonPathValidator.getShiftedEndDate")
        it.eval("import com.loyalty.nova.common.test.utils.JsonPathValidator.getShiftedTriggerDate")
    }

    @Suppress("UnsafeCast")
    fun assertJson(json: String, rules: List<ValidationRule>) {
        val document = Configuration.defaultConfiguration().jsonProvider().parse(json)

        for (rule in rules) {
            var valueList: List<Any>
            if (rule.value is String) {
                if (!rule.value.equals("")) {
                    try {
                        val eval = scriptEngine.eval(rule.value)
                        if (eval is List<*>) {
                            valueList = eval as List<Any>
                        } else {
                            valueList = listOf(eval)
                        }
                    } catch (e: ScriptException) {
                        println("Warning: ScriptException evaluating ${rule.value}")
                        valueList = rule.value.trim().split(",\\s*".toRegex())
                    }
                } else {
                    valueList = emptyList()
                }
            } else if (rule.value is List<*>) {
                valueList = rule.value as List<Any>
            } else {
                throw Error("Can't evaluate ${rule.value}")
            }

            val jsonPath = JsonPath.read<Any>(document, rule.jsonPath)

            when (jsonPath) {
                is JSONArray ->
                    if (jsonPath.size == 0) {
                        Assert.assertEquals("json path ${rule.jsonPath} validation failed", valueList, emptyList<Any>())
                    } else {
                        Assert.assertEquals(
                                "json path ${rule.jsonPath} validation failed", valueList, jsonPath)
                    }
                is Number ->
                    Assert.assertEquals(
                            "json path ${rule.jsonPath} validation failed", valueList[0], jsonPath)
                is String ->
                    when (rule.op) {
                        OpType.regex ->
                            Assert.assertThat(
                                    "json path ${rule.jsonPath} validation failed", jsonPath, Matchers.matchesPattern(valueList[0].toString()))
                        OpType.equal ->
                            Assert.assertEquals(
                                    "json path ${rule.jsonPath} validation failed", valueList[0], jsonPath)
                        else ->
                            Assert.assertEquals(
                                    "json path ${rule.jsonPath} validation failed", valueList[0], jsonPath)
                    }
                else -> throw Error("Expected JSONArray, received ${jsonPath::class}")
            }
        }
    }

    @Suppress("MagicNumber")
    fun getShiftedStartDate(hours: Long, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return OffsetDateTime.now()
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .plusHours(hours)
                .format(formatter)
    }

    @Suppress("MagicNumber")
    fun getShiftedStartDate(hours: Long): String = getShiftedStartDate(hours, "yyyy-MM-dd'T'HH:mm:ss")

    @Suppress("MagicNumber")
    fun getShiftedEndDate(hours: Long, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return OffsetDateTime.now()
                .withMinute(59)
                .withSecond(0)
                .withNano(0)
                .plusHours(hours)
                .format(formatter)
    }

    @Suppress("MagicNumber")
    fun getShiftedEndDate(hours: Long): String = getShiftedEndDate(hours, "yyyy-MM-dd'T'HH:mm:ss")

    @Suppress("MagicNumber")
    fun getShiftedTriggerDate(hours: Long): String {
        return OffsetDateTime.now()
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .plusHours(hours).toInstant().toEpochMilli().div(1000).toString()
    }
}

data class ValidationRule(val jsonPath: String, val op: OpType, val value: Any?)

enum class OpType {
    equal, like, regex
}