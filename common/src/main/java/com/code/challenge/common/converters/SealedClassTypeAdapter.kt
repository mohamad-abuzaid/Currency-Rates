package com.code.challenge.common.converters

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import kotlin.reflect.KClass

class SealedClassTypeAdapter<T : Any>(
  private val kclass: KClass<Any>,
  val gson: Gson
) : TypeAdapter<T>() {
  override fun read(jsonReader: JsonReader): T? {
    jsonReader.beginObject() // start reading the object
    val nextName = jsonReader.nextName() // get the name on the object
    val innerClass = kclass.sealedSubclasses.firstOrNull {
      it.simpleName!!.contains(nextName)
    } ?: throw Exception(
      "$nextName is not found to be a data class of the sealed class ${kclass.qualifiedName}"
    )
    val x = gson.fromJson<T>(jsonReader, innerClass.javaObjectType)
    jsonReader.endObject()
    // if there a static object, actually return that back to ensure equality and such!
    return innerClass.objectInstance as T? ?: x
  }

  override fun write(
    out: JsonWriter,
    value: T
  ) {
    val jsonString = gson.toJson(value)
    out.beginObject()
    out.name(
      value.javaClass.canonicalName?.splitToSequence(".")
        ?.last()
    )
      .jsonValue(jsonString)
    out.endObject()
  }
}