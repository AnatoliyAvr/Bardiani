package com.tolikavr.bardiani.presentation.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CustomStateFlow<T>() {
  private val _custom = MutableStateFlow(emptyList<T>())
  val custom: StateFlow<List<T>> = _custom

  fun add(t: T) {
    val tempList = _custom.value.toMutableList()
    tempList.add(t)
    _custom.value = tempList
  }
}