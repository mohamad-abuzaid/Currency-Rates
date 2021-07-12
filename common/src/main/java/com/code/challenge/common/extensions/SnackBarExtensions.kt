package com.code.challenge.common.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.code.challenge.common.R
import com.code.challenge.common.utils.Q
import me.grantland.widget.AutofitTextView

fun View.makeSnackBar(
  type: Int,
  message: String,
  actionMessage: String,
  onClick: () -> Unit
) {
  val mInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
  val snackBar = Snackbar.make(this, "", Snackbar.LENGTH_LONG)

  val customSnackView = snackBar.view as Snackbar.SnackbarLayout
  customSnackView.setBackgroundColor(ContextCompat.getColor(this.context, android.R.color.transparent))
  val textView = customSnackView.findViewById<View>(R.id.snackbar_text) as TextView
  textView.visibility = View.INVISIBLE

  val snackView = mInflater.inflate(
    when (type) {
      Q.SNACK.SUCCESS -> R.layout.success_snack_bar
      Q.SNACK.WARNING -> R.layout.warning_snack_bar
      else -> R.layout.fail_snack_bar
    },
    null
  )

  val snackMessage = snackView.findViewById(R.id.tv_snack_message) as AutofitTextView
  val snackAction = snackView.findViewById(R.id.btn_snack_action) as Button
  snackMessage.text = message
  snackAction.text = actionMessage
  snackAction.setOnClickListener {
    onClick()
    snackBar.dismiss()
  }

  customSnackView.setPadding(0, 0, 0, 0)
  customSnackView.addView(snackView, 0)
  snackBar.show()
}

fun View.makeConnectionSnackBar(
  duration: Int = 3000,
  onClick: () -> Unit
) {
  val mInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
  val snackBar = Snackbar.make(this, "", duration)

  val customSnackView = snackBar.view as Snackbar.SnackbarLayout
  customSnackView.setBackgroundColor(ContextCompat.getColor(this.context, android.R.color.transparent))
  val textView = customSnackView.findViewById<View>(R.id.snackbar_text) as TextView
  textView.visibility = View.INVISIBLE

  val snackView = mInflater.inflate(R.layout.connection_snack_bar, null)

  val snackAction = snackView.findViewById(R.id.btn_snack_action) as Button
  snackAction.setOnClickListener {
    onClick()
    snackBar.dismiss()
  }

  customSnackView.setPadding(0, 0, 0, 0)
  customSnackView.addView(snackView, 0)
  snackBar.show()
}