package com.code.challenge.features.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.code.challenge.common.extensions.layoutInflater
import com.code.challenge.databinding.ItemCurrencyBinding
import com.code.challenge.domain.features.models.LiveRatesModel

class RatesAdapter : ListAdapter<LiveRatesModel, RatesAdapter.ViewHolder>(diffUtilItemCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      ItemCurrencyBinding.inflate(parent.layoutInflater, parent, false)
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    getItem(position)?.let {
      holder.bindItem(it)
    }
  }

  inner class ViewHolder(private val binding: ItemCurrencyBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindItem(item: LiveRatesModel) {
      with(binding) {
        tvCurrName.text = item.key
        tvCurrRate.text = item.rate.toString()
        tvCurrValue.text = item.conversion.toString()
      }
    }
  }

  companion object {
    val diffUtilItemCallback = object : DiffUtil.ItemCallback<LiveRatesModel>() {
      override fun areItemsTheSame(
        oldItem: LiveRatesModel,
        newItem: LiveRatesModel,
      ): Boolean =
        oldItem.key == newItem.key

      override fun areContentsTheSame(
        oldItem: LiveRatesModel,
        newItem: LiveRatesModel,
      ): Boolean =
        oldItem == newItem
    }
  }
}