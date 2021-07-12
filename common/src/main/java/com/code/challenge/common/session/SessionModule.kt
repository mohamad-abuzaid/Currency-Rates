package com.code.challenge.common.session

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SessionModule {

  @Provides
  @Singleton
  fun provideEncryptedSharedPreferences(
    context: Context,
    masterKey: MasterKey
  ): SharedPreferences =
    EncryptedSharedPreferences.create(
      context,
      "UGU",
      masterKey,
      EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
      EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

  @Provides
  @Singleton
  fun provideMasterKey(
    context: Context
  ): MasterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()
}