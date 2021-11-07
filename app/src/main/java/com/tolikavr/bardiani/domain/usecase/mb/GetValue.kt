package com.tolikavr.bardiani.domain.usecase.mb

import com.serotonin.modbus4j.locator.BaseLocator
import com.tolikavr.bardiani.domain.repository.MbRepository

class GetValue(private val mbRepository: MbRepository) {

  suspend fun <T> execute(value: BaseLocator<T>, err: (Byte) -> Unit): T {
    return mbRepository.getValue(value, err)
  }
}