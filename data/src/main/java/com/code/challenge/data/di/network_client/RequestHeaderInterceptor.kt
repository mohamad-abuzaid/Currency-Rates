package com.code.challenge.data.di.network_client

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class RequestHeaderInterceptor @Inject constructor() : Interceptor {
  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val request: Request = chain.request()

    val maxAge = 60 * 30
    val newRequest = request.newBuilder()
      .addHeader("Cache-Control", "public, max-age=$maxAge")
      .build()

    return chain.proceed(newRequest)
  }
}