package com.tolikavr.bardiani.presentation.start

import com.serotonin.modbus4j.code.DataType
import com.serotonin.modbus4j.locator.BaseLocator
import com.serotonin.modbus4j.locator.BinaryLocator
import kotlinx.coroutines.flow.MutableStateFlow

object ValueMb {

  private fun mbGetBit(): ArrayList<BaseLocator<Boolean>> {
    val bitArray = ArrayList<BaseLocator<Boolean>>()
    bitArray.add(BinaryLocator.coilStatus(1, 0))    // 0 getStart
    bitArray.add(BinaryLocator.coilStatus(1, 1))    // 1 getValve1
    bitArray.add(BinaryLocator.coilStatus(1, 2))    // 2 getValve2
    bitArray.add(BinaryLocator.coilStatus(1, 3))    // 3 getValve3
    bitArray.add(BinaryLocator.coilStatus(1, 4))    // 4 getOpen
    bitArray.add(BinaryLocator.coilStatus(1, 5))    // 5 getClose
    bitArray.add(BinaryLocator.coilStatus(1, 8))    // 6 getModeAuto
    bitArray.add(BinaryLocator.coilStatus(1, 9))    // 7 getModeManual
    return bitArray
  }

  var start = mbGetBit()[0]
  var valve1 = mbGetBit()[1]
  var valve2 = mbGetBit()[2]
  var valve3 = mbGetBit()[3]
  var open = mbGetBit()[4]
  var close = mbGetBit()[5]
  var modeAuto = mbGetBit()[6]
  var modeManual = mbGetBit()[7]
  var time5 = BaseLocator.holdingRegister(1, 1, DataType.TWO_BYTE_INT_UNSIGNED)!!
  var time1 = BaseLocator.holdingRegister(1, 2, DataType.TWO_BYTE_INT_UNSIGNED)!!

  var connectState = MutableStateFlow(Any())
  var modelMbState = MutableStateFlow(Any())
}