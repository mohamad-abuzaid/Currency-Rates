package com.code.challenge.data.di.retrofit

import com.code.challenge.common.schedulers.qualifires.BaseUrl
import com.code.challenge.data.di.gson.GsonModule
import com.code.challenge.data.di.network_client.OkHttpClientModule
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
  includes = [
    BaseUrlModule::class,
    OkHttpClientModule::class,
    GsonModule::class
  ]
)
class RetrofitModule {

  @Provides @Singleton
  fun provideRetrofit(
    @BaseUrl baseUrl: String,
    client: OkHttpClient,
    gsonParser: Gson
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create(gsonParser))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
  }
}