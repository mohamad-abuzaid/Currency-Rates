package com.code.challenge.features.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.code.challenge.domain.common.Resource
import com.code.challenge.domain.common.ResourceType
import com.code.challenge.domain.exceptions.ChallengeCustomException
import com.code.challenge.domain.exceptions.CodeError
import com.code.challenge.domain.features.models.LiveRatesModel
import com.code.challenge.domain.features.use_case.FetchListUseCase
import com.code.challenge.domain.features.use_case.FetchLiveRatesUseCase
import com.google.gson.JsonObject
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.internal.verification.Times
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ExchangeFragmentViewModelTest {
  @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

  @Mock lateinit var fetchListUseCase: FetchListUseCase
  @Mock lateinit var fetchLiveRatesUseCase: FetchLiveRatesUseCase

  @Mock lateinit var currListLiveData: Observer<Resource<Map<String, String>>>
  @Mock lateinit var currRatesLiveData: Observer<Resource<List<LiveRatesModel>>>

  @Captor lateinit var currListCaptor: ArgumentCaptor<Resource<Map<String, String>>>
  @Captor lateinit var currRatesCaptor: ArgumentCaptor<Resource<List<LiveRatesModel>>>

  private lateinit var viewModel: ExchangeFragmentViewModel

  private val currListObject = Mockito.mock(JsonObject::class.java)
  private val liveRatesObject = Mockito.mock(JsonObject::class.java)

  private val accessKey = "2s2dd43gfdg3fd3rf3dd"
  private val source = "USD"

  @Before
  fun setUp() {
    viewModel = ExchangeFragmentViewModel(
      fetchListUseCase,
      fetchLiveRatesUseCase,
      Schedulers.trampoline(),
      Schedulers.trampoline()
    )
  }

  @After
  fun tearDown() {
    viewModel.currListResult.removeObserver(currListLiveData)
    viewModel.currRatesResult.removeObserver(currRatesLiveData)
  }

  @Test
  fun `fetchCurrList() with access_token will return Success`() {

    viewModel.currListResult.observeForever(currListLiveData)

    // act
    Mockito.lenient().`when`(fetchListUseCase(any()))
      .thenReturn(Single.just(currListObject))

    viewModel.fetchCurrList(accessKey)

    // assert

    // Loading and Success emission
    Mockito.verify(currListLiveData, Times(2)).onChanged(currListCaptor.capture())

    val values = currListCaptor.allValues
    Assert.assertEquals(ResourceType.SUCCESS, values[1].resourceType)
  }

  @Test
  fun `fetchCurrList() with access_token will return Error`() {

    viewModel.currListResult.observeForever(currListLiveData)
    val exception = ChallengeCustomException()
    exception.error = CodeError(105, "error")

    // act
    Mockito.lenient().`when`(fetchListUseCase(any()))
      .thenReturn(Single.error(exception))

    viewModel.fetchCurrList(accessKey)

    // assert

    // Loading and Success emission
    Mockito.verify(currListLiveData, Times(2)).onChanged(currListCaptor.capture())

    val values = currListCaptor.allValues
    Assert.assertEquals(ResourceType.ERROR, values[1].resourceType)
  }

  @Test
  fun `fetchLiveRates() with access_token and source valid appData will return Success`() {

    viewModel.currRatesResult.observeForever(currRatesLiveData)

    // act
    Mockito.lenient().`when`(fetchLiveRatesUseCase(any(), anyOrNull(), any()))
      .thenReturn(Single.just(liveRatesObject))

    viewModel.fetchLiveRates(accessKey, source)

    // assert

    // Loading and Success emission
    Mockito.verify(currRatesLiveData, Times(2)).onChanged(currRatesCaptor.capture())

    val values = currRatesCaptor.allValues
    Assert.assertEquals(ResourceType.SUCCESS, values[1].resourceType)
  }

  @Test
  fun `fetchLiveRates() will return Error`() {

    viewModel.currRatesResult.observeForever(currRatesLiveData)

    val exception = ChallengeCustomException()
    exception.error = CodeError(105, "error")

    // act
    Mockito.lenient().`when`(fetchLiveRatesUseCase(any(), anyOrNull(), any()))
      .thenReturn(Single.error(exception))

    viewModel.fetchLiveRates(accessKey, source)

    // assert

    // Loading and Success emission
    Mockito.verify(currRatesLiveData, Times(2)).onChanged(currRatesCaptor.capture())

    val values = currRatesCaptor.allValues
    Assert.assertEquals(ResourceType.ERROR, values[1].resourceType)
  }
}