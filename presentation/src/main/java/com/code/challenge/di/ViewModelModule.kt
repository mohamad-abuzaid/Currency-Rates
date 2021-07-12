package com.code.challenge.di

import androidx.lifecycle.ViewModel
import com.code.challenge.common.di.annotaions.ViewModelKey
import com.code.challenge.features.viewmodels.ExchangeFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(ExchangeFragmentViewModel::class)
  abstract fun bindSplashFragmentViewModel(viewModel: ExchangeFragmentViewModel): ViewModel

}