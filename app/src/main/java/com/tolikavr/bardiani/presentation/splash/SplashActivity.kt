package com.tolikavr.bardiani.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tolikavr.bardiani.presentation.MainActivity
import com.tolikavr.bardiani.databinding.SplashBinding
import com.tolikavr.bardiani.presentation.constants.mainDispatcher
import com.tolikavr.bardiani.presentation.utils.AppPreference
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

  private val activityScope = CoroutineScope(Dispatchers.Main)
  private var _binding: SplashBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = SplashBinding.inflate(layoutInflater)
    setContentView(binding.root)

    AppPreference.getPreference(applicationContext)

    activityScope.launch(mainDispatcher) {
      delay(200)
      val intent = Intent(this@SplashActivity, MainActivity::class.java)
      startActivity(intent)
      finish()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}
