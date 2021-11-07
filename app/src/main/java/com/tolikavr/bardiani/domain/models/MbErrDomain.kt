package com.tolikavr.bardiani.domain.models

import java.util.*

data class MbErrDomain(
  val id: Byte = 0,
  val name: String = "",
  val date: Date = Date(),
  val message: String? = null,
  val check_err: Boolean = false
)