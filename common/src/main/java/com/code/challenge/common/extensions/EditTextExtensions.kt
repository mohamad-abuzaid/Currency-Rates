package com.code.challenge.common.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.hideErrorOnFocus() {
  this.editText?.addTextChangedListener {
    if (it.isNullOrEmpty())
      error = null
  }
}

fun EditText.onDone(callback: () -> Unit) {
  setOnEditorActionListener { _, actionId, _ ->
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      callback()
      return@setOnEditorActionListener true
    }
    return@setOnEditorActionListener false
  }
}

fun EditText.onGo(callback: () -> Unit) {
  setOnEditorActionListener { _, actionId, _ ->
    if (actionId == EditorInfo.IME_ACTION_GO) {
      callback()
      return@setOnEditorActionListener true
    }
    return@setOnEditorActionListener false
  }
}

fun EditText.onSend(callback: () -> Unit) {
  setOnEditorActionListener { _, actionId, _ ->
    if (actionId == EditorInfo.IME_ACTION_SEND) {
      callback()
      return@setOnEditorActionListener true
    }
    return@setOnEditorActionListener false
  }
}

fun EditText.onSearch(callback: () -> Unit) {
  setOnEditorActionListener { _, actionId, _ ->
    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
      callback()
      return@setOnEditorActionListener true
    }
    return@setOnEditorActionListener false
  }
}