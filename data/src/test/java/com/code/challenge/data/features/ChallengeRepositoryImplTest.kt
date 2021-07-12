package com.code.challenge.data.features

import com.code.challenge.data.MockResponseFileReader
import com.code.challenge.data.features.datasource.ChallengeApiService
import com.code.challenge.domain.features.repositories.ChallengeRepository
import com.google.gson.Gson
import com.google.gson.JsonParseException
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class ChallengeRepositoryImplTest {

  private val server: MockWebServer = MockWebServer()
  private val MOCK_WEBSERVER_PORT = 8000
  lateinit var challengeService: ChallengeApiService
  lateinit var jsonRepository: ChallengeRepository

  @Before
  fun setup() {
    server.start(MOCK_WEBSERVER_PORT)
    challengeService = Retrofit.Builder()
      .baseUrl(server.url("/"))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create(Gson()))
      .build()
      .create(ChallengeApiService::class.java)
    jsonRepository = ChallengeRepositoryImpl(challengeService)
  }

  @After
  fun shutdown() {
    server.shutdown()
  }

  @Test
  fun `currenciesList API parse success`() {
    server.apply {
      enqueue(
        MockResponse().setBody(
          MockResponseFileReader(
            "features/ListResponseSuccess.json"
          ).content
        )
      )
    }
    jsonRepository.currenciesList("sdddfdfs34f34f3f")
      .test()
      .awaitDone(3, TimeUnit.SECONDS)
      .assertValue {
        it.get("AED").toString() == "\"United Arab Emirates Dirham\""
      }
  }

  @Test
  fun `currenciesList API parse fail`() {
    server.apply {
      enqueue(
        MockResponse().setBody(
          MockResponseFileReader("features/ListResponseError.json").content
        )
      )
    }
    jsonRepository.currenciesList("sdddfdfs34f34f3f")
      .test()
      .awaitDone(3, TimeUnit.SECONDS)
      .assertError(JsonParseException::class.java)
  }

  @Test
  fun `liveRates API parse success`() {
    server.apply {
      enqueue(
        MockResponse().setBody(
          MockResponseFileReader(
            "features/RatesResponseSuccess.json"
          ).content
        )
      )
    }
    jsonRepository.liveRates("sdddfdfs34f34f3f", "USD", 1)
      .test()
      .awaitDone(3, TimeUnit.SECONDS)
      .assertComplete()
      .assertValue { it.get("USDAED").toString().toDouble() == 3.672982 }
  }

  @Test
  fun `liveRates API parse fail`() {
    server.apply {
      enqueue(
        MockResponse().setBody(
          MockResponseFileReader(
            "features/RatesResponseError.json"
          ).content
        )
      )
    }
    jsonRepository.liveRates("sdddfdfs34f34f3f", "USD", 1)
      .test()
      .awaitDone(3, TimeUnit.SECONDS)
      .assertError(JsonParseException::class.java)
  }
}