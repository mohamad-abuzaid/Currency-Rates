package com.code.challenge.domain.features.repositories

import com.google.gson.JsonObject
import io.reactivex.Single

interface ChallengeRepository {

  fun currenciesList(
    accessKey: String,
  ): Single<JsonObject>

  fun liveRates(
    accessKey: String,
    source: String?,
    format: Int,
  ): Single<JsonObject>
}