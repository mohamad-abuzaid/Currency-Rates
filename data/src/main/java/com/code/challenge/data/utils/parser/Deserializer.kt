package com.code.challenge.data.utils.parser

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

internal class Deserializer<T> : JsonDeserializer<CodeResponse<T>> {
  @Throws(JsonParseException::class)
  override fun deserialize(
    je: JsonElement,
    type: Type,
    jdc: JsonDeserializationContext
  ): CodeResponse<T> {
    val content = je.asJsonObject

    // Deserialize it. You use a new instance of Gson to avoid infinite recursion to this deserializer
    return Gson().fromJson(content, type)
  }
}