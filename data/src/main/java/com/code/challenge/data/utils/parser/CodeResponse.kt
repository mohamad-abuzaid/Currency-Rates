package com.code.challenge.data.utils.parser

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
class CodeResponse<T>(
  val success: Boolean,
  val terms: String,
  val privacy: String,
  val source: String,
  @SerializedName("quotes", alternate = ["currencies"])
  val data: T,
) : Serializable