package mybooks.eventbus

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase
import mybooks.eventbus.v1.EventMeta

class EventMetaTypeIdResolver : TypeIdResolverBase() {

    override fun idFromValue(value: Any): String {
        return idFromValueAndType(value, value::class.java);
    }

    override fun idFromValueAndType(value: Any, suggestedType: Class<*>): String {
        return "v1"
    }

    override fun getMechanism(): JsonTypeInfo.Id {
        return JsonTypeInfo.Id.CUSTOM
    }

    override fun typeFromId(context: DatabindContext, id: String): JavaType {
        when (id) {
            "v1" -> return context.constructType(EventMeta::class.java)
            else -> throw Error("Wrong id")
        }
    }
}