package mybooks.integration.utils

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import java.time.LocalDateTime
import java.util.concurrent.ThreadLocalRandom
import javax.script.ScriptEngineManager
import javax.script.ScriptException
import kotlin.streams.asSequence

object JsonPathModifyer {

    init {
        setIdeaIoUseFallback()
    }

    val scriptEngine = ScriptEngineManager().getEngineByExtension("kts").also {
        it.eval("import mybooks.integration.utils.JsonPathModifyer.shiftStartDateHours")
        it.eval("import mybooks.integration.utils.JsonPathModifyer.shiftEndDateHours")
        it.eval("import mybooks.integration.utils.JsonPathModifyer.alphanumericStringGenerator")
    }

    fun modifyJson(json: String, transformationData: Map<String, Any>): String {
        var result: Any?
        val document = JsonPath.using(Configuration.defaultConfiguration()).parse(json)

        for ((path, value) in transformationData) {
            try {
                result = scriptEngine.eval(value.toString())
            } catch (e: ScriptException) {
                println("Warning: ScriptException evaluating $value")
                result = value
            }
            document.set(path, result)
        }
        return document.jsonString()
    }

    @Suppress("MagicNumber")
    fun shiftStartDateHours(hours: Long): Array<Int> {
        return LocalDateTime.now()
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .plusHours(hours)
                .let(LocalDateTime::toDateTimeArray)
    }

    @Suppress("MagicNumber")
    fun shiftEndDateHours(hours: Long): Array<Int> {
        return LocalDateTime.now()
                .withMinute(59)
                .withSecond(0)
                .withNano(0)
                .plusHours(hours)
                .let(LocalDateTime::toDateTimeArray)
    }


    fun alphanumericStringGenerator(length: Long): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return ThreadLocalRandom.current()
                .ints(length, 0, charPool.size)
                .asSequence()
                .map(charPool::get)
                .joinToString("")
    }
}

fun LocalDateTime.toDateTimeArray(): Array<Int> = arrayOf(year, monthValue, dayOfMonth, hour, minute)
