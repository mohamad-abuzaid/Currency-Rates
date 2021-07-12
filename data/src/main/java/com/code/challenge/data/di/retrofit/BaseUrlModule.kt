package com.code.challenge.data.di.retrofit

import com.code.challenge.common.BuildConfig.BASE_URL
import com.code.challenge.common.schedulers.qualifires.BaseUrl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseUrlModule {

  @Provides @Singleton @BaseUrl
  fun provideBaseUrl() = BASE_URL
}