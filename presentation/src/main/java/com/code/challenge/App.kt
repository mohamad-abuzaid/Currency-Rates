package com.code.challenge

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.code.challenge.common.di.ContextModule
import com.code.challenge.common.session.SessionManager
import com.code.challenge.di.AppComponent
import com.code.challenge.di.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject

class App : MultiDexApplication(), LifecycleObserver {

  lateinit var appComponent: AppComponent

  companion object {
    var appIsRunning = false
    lateinit var application: App
    var context: Context? = null
  }

  @Inject lateinit var sessionManager: SessionManager

  var wasInBackground = false

  private fun initDagger(context: Context): AppComponent {
    return DaggerAppComponent.builder()
      .contextModule(ContextModule(context))
      .build()
  }

  override fun onCreate() {
    appComponent = initDagger(this.applicationContext)
    appComponent.inject(this)
    super.onCreate()
    application = this
    context = this


    RxJavaPlugins.setErrorHandler { }

    Timber.plant(Timber.DebugTree())

    ProcessLifecycleOwner.get().lifecycle.addObserver(this)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onMoveToForeground() {
    wasInBackground = false
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun onMoveToBackground() {
    wasInBackground = true
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun onAppBackgrounded() {
    appIsRunning = false
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onAppForegrounded() {
    appIsRunning = true
  }
}