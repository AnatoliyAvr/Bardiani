package com.tolikavr.bardiani.domain.usecase.mb

import com.tolikavr.bardiani.domain.repository.MbRepository


class ConnectToModBus(private val mbRepository: MbRepository) {

  suspend fun execute(host: String, port: String) {
    mbRepository.connectToDatabase(host, port)
  }
}