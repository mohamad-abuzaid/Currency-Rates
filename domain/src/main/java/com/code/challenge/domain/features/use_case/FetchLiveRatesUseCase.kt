package com.code.challenge.domain.features.use_case

import com.code.challenge.domain.features.repositories.ChallengeRepository
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class FetchLiveRatesUseCase @Inject constructor(
  private val challengeRepository: ChallengeRepository,
) {

  operator fun invoke(
    accessKey: String,
    source: String?,
    format: Int,
  ): Single<JsonObject> = challengeRepository.liveRates(accessKey, source, format)
}