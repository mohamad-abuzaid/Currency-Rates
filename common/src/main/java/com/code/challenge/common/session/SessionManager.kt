package com.code.challenge.common.session

import android.content.SharedPreferences
import com.code.challenge.common.extensions.put
import com.code.challenge.common.extensions.remove
import com.google.gson.Gson
import javax.inject.Inject

class SessionManager @Inject constructor(
  private val sharedPreferences: SharedPreferences,
) {

  companion object {
    private const val ACCESS_TOKEN_KEY = "access_token_key"
  }

  fun saveAccessToken(accessToken: String?) = sharedPreferences.put(ACCESS_TOKEN_KEY, accessToken)
  fun getSavedAccessToken(): String? = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
  private fun removeAccessToken() = sharedPreferences.remove(ACCESS_TOKEN_KEY)

}