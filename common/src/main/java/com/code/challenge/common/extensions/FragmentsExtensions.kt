package com.code.challenge.common.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

@Suppress("unused")
fun Fragment.hideKeyboard() {
  val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
  imm?.hideSoftInputFromWindow(view?.rootView?.windowToken, 0)
}


fun View.visible(visible: Boolean) {
  visibility = if (visible) View.VISIBLE else View.GONE
}


val ViewGroup.layoutInflater: LayoutInflater get() = LayoutInflater.from(this.context)