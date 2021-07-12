package com.code.challenge.common.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

fun String.fromHtml(): Spanned {
  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
  } else {
    Html.fromHtml(this)
  }
}

fun String.toTimeInMillis(): Long {
  val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
  dateFormat.timeZone = TimeZone.getTimeZone("UTC")
  val pasTime: Date = dateFormat.parse(this) ?: Date()
  return pasTime.time
}

fun Long.toDateStr(): String? {
  val date = Date(this)
  val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
  dateFormat.timeZone = TimeZone.getTimeZone("UTC")
  return dateFormat.format(date)
}

fun String.toThousandsOrMillions(): String = when {
  abs(this.toLong() / 1000) > 1 -> {
    (this.toLong() / 1000).toString() + "k"
  }
  abs(this.toLong() / 1000000) > 1 -> {
    (this.toLong() / 1000000).toString() + "m"
  }
  else -> this
}