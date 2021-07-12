package com.code.challenge.data.features

import com.code.challenge.data.features.datasource.ChallengeApiService
import com.code.challenge.domain.features.repositories.ChallengeRepository
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
  private val challengeService: ChallengeApiService,
) : ChallengeRepository {

  override fun currenciesList(accessKey: String): Single<JsonObject> =
    challengeService.currenciesList(accessKey).map { response ->
      response.data
    }

  override fun liveRates(accessKey: String, source: String?, format: Int): Single<JsonObject> =
    challengeService.liveRates(accessKey, source, format).map { response ->
      response.data
    }
}