package dev.mednikov.expensetracking.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.localDate

class DateSerializer: JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    override fun serialize(src: LocalDate?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return JsonPrimitive(src!!.format(formatter));
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDate? {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return  LocalDate.parse(json!!.getAsJsonPrimitive().getAsString(), formatter);
    }
}