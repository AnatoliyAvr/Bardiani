package com.tolikavr.bardiani.presentation.constants

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

const val DELAY_10 = 10L
const val DELAY_100 = 100L
