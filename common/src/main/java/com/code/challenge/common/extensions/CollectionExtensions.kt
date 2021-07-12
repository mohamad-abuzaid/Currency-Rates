package com.code.challenge.common.extensions


fun <T> MutableList<T>.removeDuplicates(): MutableList<T> {
  val uniqueList = mutableListOf<T>()
  this.forEach {
    if (!uniqueList.contains(it)) uniqueList.add(it)
  }
  return uniqueList
}