package com.code.challenge.domain.features.use_case

import com.code.challenge.domain.features.repositories.ChallengeRepository
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class FetchListUseCase @Inject constructor(
  private val challengeRepository: ChallengeRepository,
) {

  operator fun invoke(
    accessKey: String,
  ): Single<JsonObject> = challengeRepository.currenciesList(accessKey)
}