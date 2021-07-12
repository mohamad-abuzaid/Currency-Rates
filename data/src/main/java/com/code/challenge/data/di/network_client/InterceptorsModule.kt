package com.code.challenge.data.di.network_client

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
class InterceptorsModule {

  @Provides
  @Singleton
  @Named(DaggerConstants.REQ_ERROR_INTERCEPTOR)
  fun providesRequestErrorInterceptor(appContext: Context): Interceptor = RequestErrorInterceptor(appContext)
}