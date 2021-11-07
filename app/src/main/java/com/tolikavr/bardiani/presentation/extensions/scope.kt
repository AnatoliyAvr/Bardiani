package com.tolikavr.bardiani.presentation.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.tolikavr.bardiani.presentation.constants.defaultDispatcher
import com.tolikavr.bardiani.presentation.constants.mainDispatcher
import kotlinx.coroutines.*

fun CoroutineScope.cycle(
  dispatcher: CoroutineDispatcher = defaultDispatcher,
  delay: Long = 10L,
  block: suspend CoroutineScope.() -> Unit
): Job {
  return this.launch(dispatcher) {
    while (this@cycle.isActive) {
      block()
      delay(delay)
    }
  }
}

fun CoroutineScope.getValue(
  fragment: Fragment,
  lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
  block: suspend CoroutineScope.() -> Unit
) {
  this.launch(mainDispatcher) {
    fragment.lifecycle.repeatOnLifecycle(lifecycleState) {
      block()
    }
  }
}