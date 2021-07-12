package com.code.challenge.utils.extensions

import android.app.Activity
import android.app.Service
import androidx.fragment.app.Fragment
import com.code.challenge.App
import com.code.challenge.di.AppComponent

/**
 * enable dagger AppComponent for any Activity
 */
val Activity.appComponent: AppComponent get() = (application as App).appComponent

/**
 * enable dagger AppComponent for any Fragment
 */
val Fragment.appComponent: AppComponent get() = (context?.applicationContext as App).appComponent

/**
 * enable dagger AppComponent for any Service
 */
val Service.appComponent: AppComponent get() = (baseContext?.applicationContext as App).appComponent