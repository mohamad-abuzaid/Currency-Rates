package com.code.challenge.data.features.datasource

import com.code.challenge.data.utils.Endpoints.Currency
import com.code.challenge.data.utils.parser.CodeResponse
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ChallengeApiService {

  @GET(Currency.List)
  fun currenciesList(
    @Query("access_key") accessKey: String,
  ): Single<CodeResponse<JsonObject>>

  @GET(Currency.Live)
  fun liveRates(
    @Query("access_key") accessKey: String,
    @Query("source") source: String?,
    @Query("format") format: Int,
  ): Single<CodeResponse<JsonObject>>

}