package com.tolikavr.bardiani.data.storage.mb

import com.serotonin.modbus4j.ModbusFactory
import com.serotonin.modbus4j.exception.ErrorResponseException
import com.serotonin.modbus4j.exception.ModbusInitException
import com.serotonin.modbus4j.exception.ModbusTransportException
import com.serotonin.modbus4j.ip.IpParameters
import com.serotonin.modbus4j.ip.tcp.TcpMaster
import com.serotonin.modbus4j.locator.BaseLocator
import kotlinx.coroutines.delay
import timber.log.Timber

class MbStorageImpl : MbStorage {

  private lateinit var master: TcpMaster

  override suspend fun <T> setValue(value1: BaseLocator<T>, value2: Any, err: (Byte) -> Unit) {
    try {
      master.isInitialized
      master.setValue(value1, value2)
    } catch (e: ModbusTransportException) {
      Timber.d("setValue1 ${e.message}")
    } catch (e: ErrorResponseException) {
      Timber.d("setValue2 ${e.message}")
      err(e.errorResponse.exceptionCode)
    } catch (e: UninitializedPropertyAccessException) {
      Timber.d("isInitialized ${e.message}")
    }
  }

  override suspend fun <T> getValue(value: BaseLocator<T>, err: (Byte) -> Unit): T {
    var result = Any() as T
    try {
      master.isInitialized
      result = master.getValue(value)
    } catch (e: ModbusTransportException) {
      Timber.d("getValue1 ${e.message}")
    } catch (e: ErrorResponseException) {
      err(e.errorResponse.exceptionCode)
      Timber.d("getValue2 ${e.message}")
    } catch (e: UninitializedPropertyAccessException) {
      Timber.d("isInitialized ${e.message}")
    }
    return result
  }

  override suspend fun connectToDatabase(host: String, port: String) {
    initialization(host, port)
    while (true) {
      try {
        master.init()
        break
      } catch (e: ModbusInitException) {
        Timber.d("connectToDatabase ${e.message}")
        delay(1000)
      }
    }
  }

  override fun isConnected(): Boolean {
    var result = false
    try {
      master.isInitialized
      result = master.testSlaveNode(1)
    } catch (e: UninitializedPropertyAccessException) {
      Timber.d("isInitialized ${e.message}")
    }
    return result
  }

  private fun initialization(setHost: String, setPort: String) {
    val ipParameters: IpParameters = IpParameters().apply {
      host = setHost
      port = setPort.toInt()
    }
    val modbusFactory = ModbusFactory()
    master = modbusFactory.createTcpMaster(ipParameters, true) as TcpMaster
  }
}
