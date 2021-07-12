package com.code.challenge.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.code.challenge.common.extensions.visible
import com.code.challenge.features.ChallengeActivity

fun Activity.showProgressWheel(show: Boolean) {
  if (show) {
    window.setFlags(
      WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
      WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
  } else {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
  }

  when (this) {
    is ChallengeActivity -> binding.progressBar.visible(show)
  }
}

fun Fragment.closeFragment() {
  activity?.supportFragmentManager?.popBackStackImmediate()
}

@Suppress("unused")
fun Activity.hideKeyboard() {
  val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
  var view: View? = currentFocus
  // If no view currently has focus, create a new one, just so we can grab a window token from it
  if (view == null) {
    view = View(this)
  }
  imm?.hideSoftInputFromWindow(view.windowToken, 0)
}