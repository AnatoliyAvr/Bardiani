package com.tolikavr.bardiani.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tolikavr.bardiani.databinding.ActivityMainBinding
import com.tolikavr.bardiani.presentation.utils.hideSystemUI

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    hideSystemUI(window, binding.root)
  }
}