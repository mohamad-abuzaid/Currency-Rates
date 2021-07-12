package com.code.challenge.data.di.gson

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.code.challenge.common.converters.SealedClassTypeAdapter
import com.code.challenge.data.utils.parser.Deserializer
import com.code.challenge.data.utils.parser.CodeResponse
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlin.jvm.internal.Reflection

@Module
class GsonModule {
  private val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

  @Provides
  @Singleton
  fun providesGson(): Gson =
    GsonBuilder()
      .setDateFormat(DATE_FORMAT)
      .setLenient()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
      .registerTypeAdapter(CodeResponse::class.java, Deserializer<CodeResponse<Any>>())
      .registerTypeAdapterFactory(
        object : TypeAdapterFactory {
          override fun <T : Any> create(
            gson: Gson,
            type: TypeToken<T>
          ): TypeAdapter<T> {
            val kclass = Reflection.getOrCreateKotlinClass(type.rawType)
            return if (kclass.sealedSubclasses.any()) {
              SealedClassTypeAdapter<T>(kclass, gson)
            } else
              gson.getDelegateAdapter(this, type)
          }
        })
      .create()
}