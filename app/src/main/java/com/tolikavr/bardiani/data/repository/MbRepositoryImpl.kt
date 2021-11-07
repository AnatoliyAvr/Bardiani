package com.tolikavr.bardiani.data.repository

import com.serotonin.modbus4j.locator.BaseLocator
import com.tolikavr.bardiani.data.storage.mb.MbStorage
import com.tolikavr.bardiani.domain.repository.MbRepository

class MbRepositoryImpl(private val mbStorage: MbStorage) : MbRepository {

  override suspend fun <T> setValue(value1: BaseLocator<T>, value2: Any, err: (Byte) -> Unit) {
    mbStorage.setValue(value1, value2, err)
  }

  override suspend fun <T> getValue(value: BaseLocator<T>, err: (Byte) -> Unit): T {
    return mbStorage.getValue(value, err)
  }

  override suspend fun connectToDatabase(host: String, port: String) {
    mbStorage.connectToDatabase(host, port)
  }

  override fun isConnected(): Boolean {
    return mbStorage.isConnected()
  }
}