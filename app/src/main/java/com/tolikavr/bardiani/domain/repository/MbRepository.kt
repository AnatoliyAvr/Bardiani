package com.tolikavr.bardiani.domain.repository

import com.serotonin.modbus4j.locator.BaseLocator

interface MbRepository {

  suspend fun <T> setValue(value1: BaseLocator<T>, value2: Any, err: (Byte) -> Unit)

  suspend fun <T> getValue(value: BaseLocator<T>, err: (Byte) -> Unit): T

  suspend fun connectToDatabase(host: String, port: String)

  fun isConnected(): Boolean
}
