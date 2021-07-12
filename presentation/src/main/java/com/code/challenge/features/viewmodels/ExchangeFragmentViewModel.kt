package com.code.challenge.features.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.code.challenge.common.base.BaseViewModel
import com.code.challenge.common.extensions.toResource
import com.code.challenge.common.schedulers.qualifires.Background
import com.code.challenge.common.schedulers.qualifires.ForeGround
import com.code.challenge.domain.common.Resource
import com.code.challenge.domain.features.models.LiveRatesModel
import com.code.challenge.domain.features.use_case.FetchListUseCase
import com.code.challenge.domain.features.use_case.FetchLiveRatesUseCase
import com.google.gson.JsonObject
import io.reactivex.Scheduler
import javax.inject.Inject

class ExchangeFragmentViewModel @Inject constructor(
  private val fetchListUseCase: FetchListUseCase,
  private val fetchLiveRatesUseCase: FetchLiveRatesUseCase,
  @Background private val backgroundScheduler: Scheduler,
  @ForeGround private val foregroundScheduler: Scheduler,
) : BaseViewModel() {

  val currenciesRatesList: MutableList<LiveRatesModel> = mutableListOf()

  private val _currListResult = MutableLiveData<Resource<Map<String, String>>>()
  val currListResult: LiveData<Resource<Map<String, String>>> get() = _currListResult

  private val _currRatesResult = MutableLiveData<Resource<List<LiveRatesModel>>>()
  val currRatesResult: LiveData<Resource<List<LiveRatesModel>>> = _currRatesResult

  fun fetchCurrList(
    accessKey: String,
  ) {
    add(
      fetchListUseCase(accessKey)
        .subscribeOn(backgroundScheduler)
        .observeOn(foregroundScheduler)
        .doOnSubscribe { _currListResult.value = Resource.loading() }
        .subscribe(
          {
            val currMap = parseJsonObject(it)
            _currListResult.value = Resource.success(currMap)
          },
          {
            _currListResult.value = it.toResource()
          }
        )
    )
  }

  fun fetchLiveRates(
    accessKey: String,
    source: String? = null,
  ) {
    add(
      fetchLiveRatesUseCase(accessKey, source, 1)
        .subscribeOn(backgroundScheduler)
        .observeOn(foregroundScheduler)
        .doOnSubscribe { _currRatesResult.value = Resource.loading() }
        .subscribe(
          {
            currenciesRatesList.clear()

            val ratesMap = parseJsonObject(it)
            for (key in ratesMap.keys) {
              currenciesRatesList.add(LiveRatesModel(key, (ratesMap[key] ?: "0").toDouble()))
            }
            _currRatesResult.value = Resource.success(currenciesRatesList)
          },
          {
            _currRatesResult.value = it.toResource()
          }
        )
    )
  }

  fun calculateConversion(amount: Double) {
    currenciesRatesList.forEach {
      it.conversion = amount * it.rate
    }
  }

  private fun parseJsonObject(jObject: JsonObject): Map<String, String> {
    val mapResult = mutableMapOf<String, String>()

    for (key: String in jObject.keySet()) {
      mapResult[key] = jObject.get(key).toString()
    }

    return mapResult
  }
}