package com.tolikavr.bardiani.domain.usecase.mb

import com.tolikavr.bardiani.domain.repository.MbErrRepository

class UpdateDatabase(private val mbErrRepository: MbErrRepository) {

  suspend fun execute(id: Byte) {
    mbErrRepository.updateDatabase(id)
  }
}