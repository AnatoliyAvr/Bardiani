package com.tolikavr.bardiani.domain.usecase.mb

import com.serotonin.modbus4j.locator.BaseLocator
import com.tolikavr.bardiani.domain.repository.MbRepository

class SetValue(private val mbRepository: MbRepository) {

  suspend fun <T> execute(value1: BaseLocator<T>, value2: Any, err: (Byte) -> Unit) {
    mbRepository.setValue(value1, value2, err)
  }
}