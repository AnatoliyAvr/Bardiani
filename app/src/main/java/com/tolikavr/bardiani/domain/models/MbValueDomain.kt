package com.tolikavr.bardiani.domain.models

data class MbValueDomain(
  var getStart: Boolean = false,
  var getValve1: Boolean = false,
  var getValve2: Boolean = false,
  var getValve3: Boolean = false,
  var getOpen: Boolean = false,
  var getClose: Boolean = false,
  var getError: Boolean = false,
  var getModeAuto: Boolean = false,
  var getModeManual: Boolean = false,
  var getConnectPlc: Boolean = false
)