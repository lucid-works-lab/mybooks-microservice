package mybooks.eventbus

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase
import mybooks.events.v1.BookAdded
import mybooks.events.v1.BookRemoved

class EventDataTypeIdResolver : TypeIdResolverBase() {

    override fun idFromValue(value: Any): String {
        return idFromValueAndType(value, value::class.java);
    }

    override fun idFromValueAndType(value: Any, suggestedType: Class<*>): String {
        when (value) {
            is BookAdded -> return "BookAdded_v1"
            is BookRemoved -> return "BookRemoved_v1"
            else -> throw Error("Unknown class")
        }
    }

    override fun getMechanism(): JsonTypeInfo.Id {
        return JsonTypeInfo.Id.CUSTOM
    }

    override fun typeFromId(context: DatabindContext, id: String): JavaType {
        when (id) {
            "BookAdded_v1" -> return context.constructType(BookAdded::class.java)
            "BookRemoved_v1" -> return context.constructType(BookRemoved::class.java)
            else -> throw Error("Wrong id")
        }
    }
}