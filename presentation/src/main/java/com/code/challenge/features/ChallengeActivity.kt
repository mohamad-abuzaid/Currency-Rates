package com.code.challenge.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.code.challenge.databinding.ActivityChallengeBinding
import com.code.challenge.utils.extensions.appComponent

class ChallengeActivity : AppCompatActivity() {
  lateinit var binding: ActivityChallengeBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appComponent.inject(this)
    binding = ActivityChallengeBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }

}