package com.code.challenge.data.di.network_client

import android.content.Context
import com.code.challenge.domain.exceptions.ChallengeCustomException
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import timber.log.Timber
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject

class RequestErrorInterceptor @Inject constructor(
  private val appContext: Context,
) : Interceptor {
  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val request: Request = chain.request()
    val response = chain.proceed(request)
    val body: ResponseBody? = response.peekBody(Long.MAX_VALUE)
    body?.contentType()
      ?.let {
        if (it.subtype.equals("json", true)) {
          val errorCode = response.code // Assume default OK
          var challengeException = ChallengeCustomException()

          if (errorCode in 400..499) {
            try {
              val source: BufferedSource = body.source()
              source.request(Long.MAX_VALUE) // Buffer the entire body.
              val buffer: Buffer = source.buffer
              val charset: Charset =
                it.charset(Charset.forName("UTF-8")) ?: Charset.defaultCharset()

              val json: String = buffer.clone()
                .readString(charset)

              challengeException = Gson().fromJson(json, ChallengeCustomException::class.java)
            } catch (e: Exception) {
              Timber.tag("Error Interceptor")
                .e("Error: %s", e.message)
            }

            // Check if status has an error code then throw and exception so retrofit can trigger the onFailure callback method.
            // Anything above 400 is treated as a server error.
            throw challengeException
          }
        }
      }

    return response
  }
}