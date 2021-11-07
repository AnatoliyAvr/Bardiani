package com.tolikavr.bardiani.domain.usecase.mb

import com.tolikavr.bardiani.domain.models.MbErrDomain
import com.tolikavr.bardiani.domain.repository.MbErrRepository

class LoadDatabase(private val mbErrRepository: MbErrRepository) {

  suspend fun execute(): List<MbErrDomain> {
    return mbErrRepository.loadDatabase()
  }
}