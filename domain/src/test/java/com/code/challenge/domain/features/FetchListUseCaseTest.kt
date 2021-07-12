package com.code.challenge.domain.features

import com.code.challenge.domain.features.repositories.ChallengeRepository
import com.code.challenge.domain.features.use_case.FetchListUseCase
import com.code.challenge.domain.features.utils.RxImmediateSchedulerRule
import com.google.gson.JsonObject
import com.nhaarman.mockito_kotlin.any
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchListUseCaseTest {

  @get:Rule var rxImmediateSchedulerRule = RxImmediateSchedulerRule()

  @Mock lateinit var challengeRepository: ChallengeRepository
  private val currListObject = Mockito.mock(JsonObject::class.java)

  @Test
  fun `FetchListUseCase invoke() with access_key will call currenciesList() return JsonObject`() {
    // arrange
    val accessKey = "2s2dd43gfdg3fd3rf3dd"

    // act
    Mockito.`when`(challengeRepository.currenciesList(any()))
      .thenReturn(Single.just(currListObject))

    val useCase = FetchListUseCase(challengeRepository)
    val resultObserver = useCase(accessKey).test()

    // assert
    resultObserver.assertValue(currListObject)
    resultObserver.dispose()
  }
}