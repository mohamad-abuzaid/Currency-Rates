package com.code.challenge.features.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code.challenge.BuildConfig
import com.code.challenge.R
import com.code.challenge.common.base.views.BaseFragmentViewBinding
import com.code.challenge.common.di.ViewModelFactory
import com.code.challenge.common.extensions.makeSnackBar
import com.code.challenge.common.extensions.onGo
import com.code.challenge.common.extensions.visible
import com.code.challenge.common.utils.Q
import com.code.challenge.databinding.FragmentExchangeBinding
import com.code.challenge.domain.common.Resource
import com.code.challenge.domain.common.ResourceType.LOADING
import com.code.challenge.domain.common.ResourceType.SUCCESS
import com.code.challenge.domain.features.models.LiveRatesModel
import com.code.challenge.features.adapters.RatesAdapter
import com.code.challenge.features.viewmodels.ExchangeFragmentViewModel
import com.code.challenge.utils.extensions.appComponent
import com.code.challenge.utils.extensions.showProgressWheel
import javax.inject.Inject

class ExchangeFragment : BaseFragmentViewBinding<FragmentExchangeBinding>() {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private val exchangeFragmentViewModel: ExchangeFragmentViewModel by viewModels { viewModelFactory }

  private var currenciesListAdapter: ArrayAdapter<String>? = null
  private val ratesAdapter: RatesAdapter by lazy { RatesAdapter() }

  override fun onBind(
    inflater: LayoutInflater,
    container: ViewGroup?,
  ): FragmentExchangeBinding {
    appComponent.inject(this)
    return FragmentExchangeBinding.inflate(inflater, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?,
  ) {
    super.onViewCreated(view, savedInstanceState)

    setupRatesRecycler()
    initCurrenciesSpinner()
    initClickListeners()
    fetchInitialData()
    initObservers()
  }

  private fun setupRatesRecycler() {
    val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    binding.rvRates.layoutManager = layoutManager
    binding.rvRates.adapter = ratesAdapter
  }

  private fun initCurrenciesSpinner() {
    currenciesListAdapter = ArrayAdapter(
      requireContext(),
      R.layout.dropdown_menu_popup_item,
      mutableListOf<String>()
    )

    binding.drpCurrencies.adapter = currenciesListAdapter

    binding.drpCurrencies.onItemSelectedListener = object : OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {

      }

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        exchangeFragmentViewModel.fetchLiveRates(
          BuildConfig.API_KEY,
          currenciesListAdapter?.getItem(position) ?: "USD",
        )
      }
    }
  }

  private fun initClickListeners() {
    binding.etCurrValue.onGo(
      callback = {
        fetchRates()
      }
    )

    binding.btnCurrExchange.setOnClickListener {
      fetchRates()
    }
  }

  private fun fetchRates() {
    if (binding.etCurrValue.text.isNullOrEmpty().not()) {
      val amount = binding.etCurrValue.text.toString()
      exchangeFragmentViewModel.calculateConversion(amount.toDouble())
      ratesAdapter.submitList(exchangeFragmentViewModel.currenciesRatesList)
      ratesAdapter.notifyDataSetChanged()

    } else {
      binding.ratesContainer.makeSnackBar(
        Q.SNACK.WARNING,
        getString(R.string.enter_amount),
        getString(R.string.dismiss)
      ) {}
    }
  }

  private fun fetchInitialData() {
    exchangeFragmentViewModel.fetchCurrList(BuildConfig.API_KEY)
  }

  private fun initObservers() {
    exchangeFragmentViewModel.currListResult.observe(viewLifecycleOwner, this::currListObserver)
    exchangeFragmentViewModel.currRatesResult.observe(viewLifecycleOwner, this::currRatesObserver)
  }

  private fun currListObserver(result: Resource<Map<String, String>>) {
    when (result.resourceType) {
      LOADING -> activity?.showProgressWheel(true)
      SUCCESS -> {
        activity?.showProgressWheel(false)
        result.data?.let {
          updateCurrencies(it.keys.toMutableList())
        }
        exchangeFragmentViewModel.fetchLiveRates(BuildConfig.API_KEY)
      }
      else -> {
        activity?.showProgressWheel(false)
        binding.ratesContainer.makeSnackBar(
          Q.SNACK.FAIL,
          getString(R.string.server_error_message),
          getString(R.string.dismiss)
        ) {}
      }
    }
  }

  private fun currRatesObserver(result: Resource<List<LiveRatesModel>>) {
    when (result.resourceType) {
      LOADING -> activity?.showProgressWheel(true)
      SUCCESS -> {
        activity?.showProgressWheel(false)
        binding.tvEmptyRates.visible(false)
        binding.rvRates.visible(true)

        result.data?.let {
          ratesAdapter.submitList(it)
        }
      }
      else -> {
        activity?.showProgressWheel(false)
        binding.tvEmptyRates.visible(true)
        binding.rvRates.visible(false)

        binding.ratesContainer.makeSnackBar(
          Q.SNACK.FAIL,
          getString(R.string.server_error_message),
          getString(R.string.dismiss)
        ) {}
      }
    }
  }

  private fun updateCurrencies(currencies: MutableList<String>) {
    currenciesListAdapter?.clear()
    currenciesListAdapter?.addAll(currencies)
    currenciesListAdapter?.notifyDataSetChanged()
  }

  override fun onDetach() {
    activity?.showProgressWheel(false)
    super.onDetach()
  }
}