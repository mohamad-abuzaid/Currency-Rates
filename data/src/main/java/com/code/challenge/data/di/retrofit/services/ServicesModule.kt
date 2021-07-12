package com.code.challenge.data.di.retrofit.services

import com.code.challenge.data.di.retrofit.RetrofitModule
import com.code.challenge.data.features.datasource.ChallengeApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
  includes = [
    RetrofitModule::class
  ]
)
class ServicesModule {

  @Provides
  @Singleton
  fun provideAuthServices(
    retrofit: Retrofit
  ): ChallengeApiService {
    return retrofit.create(ChallengeApiService::class.java)
  }
}