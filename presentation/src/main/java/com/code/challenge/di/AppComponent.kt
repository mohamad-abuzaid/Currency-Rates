package com.code.challenge.di

import com.code.challenge.App
import com.code.challenge.common.di.ContextModule
import com.code.challenge.common.schedulers.SchedulersModule
import com.code.challenge.common.session.SessionModule
import com.code.challenge.data.di.RepositoriesModule
import com.code.challenge.features.ChallengeActivity
import com.code.challenge.features.fragments.ExchangeFragment
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    ContextModule::class,
    SessionModule::class,
    SchedulersModule::class,
    RepositoriesModule::class,
    ViewModelModule::class
  ]
)
interface AppComponent {

  fun inject(app: App)

  fun getOkHttpClient(): OkHttpClient

  fun inject(target: ChallengeActivity)

  fun inject(target: ExchangeFragment)
}