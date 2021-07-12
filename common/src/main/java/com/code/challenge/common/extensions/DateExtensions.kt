package com.code.challenge.common.extensions

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
fun convertDateToTimeStamp(dateStr: String): Long {
  val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
  val date: Date = formatter.parse(dateStr) as Date
  return date.time
}

fun convertTimeStampToDate(timeStamp: Long): String? {
  val calendar = Calendar.getInstance(Locale.ENGLISH)
  calendar.timeInMillis = timeStamp * 1000L
  return android.text.format.DateFormat.format("dd/MM/yyyy", calendar).toString()
}

fun utcInMilli(): Long = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis