package com.code.challenge.data.di.network_client

import android.util.Log.VERBOSE
import com.code.challenge.common.di.ContextModule
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module(
  includes = [
    ContextModule::class,
    InterceptorsModule::class
  ]
)
class OkHttpClientModule {

  @Provides
  @Singleton
  fun providesOkHttpClient(
    requestHeaderInterceptor: RequestHeaderInterceptor,
    @Named(DaggerConstants.REQ_ERROR_INTERCEPTOR) requestErrorInterceptor: Interceptor
  ): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
      .connectTimeout(60, TimeUnit.SECONDS)
      .readTimeout(60, TimeUnit.SECONDS)
      .writeTimeout(1, TimeUnit.HOURS)
      .addNetworkInterceptor(requestHeaderInterceptor)
      .addInterceptor(requestErrorInterceptor)
      .addInterceptor(
        LoggingInterceptor.Builder()
          .setLevel(Level.BASIC)
          .log(VERBOSE)
          .build()
      )

    return okHttpClientBuilder.build()
  }
}