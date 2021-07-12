package com.code.challenge.common.schedulers

import com.code.challenge.common.schedulers.qualifires.Background
import com.code.challenge.common.schedulers.qualifires.ForeGround
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class SchedulersModule {

  @Provides
  @Singleton
  @ForeGround
  fun providesForegroundScheduler(): Scheduler {
    return AndroidSchedulers.mainThread()
  }

  @Provides
  @Singleton
  @Background
  fun providesBackgroundScheduler(): Scheduler {
    return Schedulers.io()
  }
}