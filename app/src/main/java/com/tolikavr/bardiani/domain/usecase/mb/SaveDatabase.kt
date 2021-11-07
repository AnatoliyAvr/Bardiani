package com.tolikavr.bardiani.domain.usecase.mb

import com.tolikavr.bardiani.domain.models.MbErrDomain
import com.tolikavr.bardiani.domain.repository.MbErrRepository

class SaveDatabase(private val mbErrRepository: MbErrRepository) {

  suspend fun execute(mbErrDomain: MbErrDomain) {
    mbErrRepository.saveDatabase(mbErrDomain)
  }
}