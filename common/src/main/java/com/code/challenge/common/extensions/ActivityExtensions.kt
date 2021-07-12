package com.code.challenge.common.extensions

import android.content.Context
import android.content.Intent

fun Context.startActivityAndClearStack(intent: Intent) {
  intent.addFlags(
    Intent.FLAG_ACTIVITY_CLEAR_TOP or
      Intent.FLAG_ACTIVITY_CLEAR_TASK or
      Intent.FLAG_ACTIVITY_NEW_TASK
  )
  startActivity(intent)
}