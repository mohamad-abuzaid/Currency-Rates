package com.code.challenge.domain.features.models

import androidx.annotation.Keep

@Keep
class LiveRatesModel(
  val key: String,
  val rate: Double,
  var conversion: Double = 0.0,
)