package com.code.challenge.domain.features

import com.code.challenge.domain.features.repositories.ChallengeRepository
import com.code.challenge.domain.features.use_case.FetchLiveRatesUseCase
import com.code.challenge.domain.features.utils.RxImmediateSchedulerRule
import com.google.gson.JsonObject
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchLiveRatesUseCaseTest {

  @get:Rule var rxImmediateSchedulerRule = RxImmediateSchedulerRule()

  @Mock lateinit var challengeRepository: ChallengeRepository
  private val liveRatesObject = Mockito.mock(JsonObject::class.java)

  @Test
  fun `FetchLiveRatesUseCase invoke() with currency Data will call liveRates() and return JsonObject`() {
    // arrange
    val accessKey = "2s2dd43gfdg3fd3rf3dd"
    val source = "USD"
    val format = 1

    // act
    Mockito.`when`(challengeRepository.liveRates(any(), anyOrNull(), any()))
      .thenReturn(Single.just(liveRatesObject))

    val useCase = FetchLiveRatesUseCase(challengeRepository)
    val resultObserver = useCase(accessKey, source, format).test()

    // assert
    resultObserver.assertValue(liveRatesObject)
    resultObserver.dispose()
  }
}