package com.tolikavr.bardiani.presentation.utils

import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun hideSystemUI(window: Window, view: View) {
  WindowCompat.setDecorFitsSystemWindows(window, false)
  WindowInsetsControllerCompat(window, view).let { controller ->
    controller.hide(WindowInsetsCompat.Type.systemBars())
    controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
  }
}

fun showSystemUI(window: Window, view: View) {
  WindowCompat.setDecorFitsSystemWindows(window, true)
  WindowInsetsControllerCompat(window, view).show(WindowInsetsCompat.Type.systemBars())
}