package com.tolikavr.bardiani.domain.usecase.mb

import com.tolikavr.bardiani.domain.repository.MbRepository

class Connected(private val mbRepository: MbRepository) {

  fun execute(): Boolean {
    return mbRepository.isConnected()
  }
}