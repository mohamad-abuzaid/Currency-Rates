package com.code.challenge.common.extensions

import com.code.challenge.domain.common.Resource
import com.code.challenge.domain.exceptions.ChallengeCustomException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Throwable.toResource(
  data: T? = null,
  otherError: (message: String) -> Resource<T> = { Resource.unknownError(it) },
): Resource<T> {
  this.printStackTrace()
  return when (this) {
    is ChallengeCustomException -> Resource.error(data)
    is UnknownHostException -> Resource.connectionError(data)
    is SocketTimeoutException -> Resource.connectionError(data)
    else -> otherError(this.message.toString())
  }
}