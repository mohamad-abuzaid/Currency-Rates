package com.code.challenge.data.di

import com.code.challenge.data.di.retrofit.services.ServicesModule
import com.code.challenge.data.features.ChallengeRepositoryImpl
import com.code.challenge.domain.features.repositories.ChallengeRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
  includes = [
    ServicesModule::class
  ]
)
abstract class RepositoriesModule {

  @Binds @Singleton
  abstract fun bindAuthenticationRepository(
    challengeRepository: ChallengeRepositoryImpl
  ): ChallengeRepository

}