package com.code.challenge.domain.exceptions

import com.google.gson.annotations.SerializedName

open class ChallengeCustomException(var type: String = "") : Exception() {

  @SerializedName("success") var success: Boolean = false

  @SerializedName("error")
  open var error: CodeError? = null
}

class CodeError(
  @SerializedName("code")
  val code: Int,

  @SerializedName("info")
  val info: String,
)