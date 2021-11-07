package com.tolikavr.bardiani.presentation.start

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolikavr.bardiani.R
import com.tolikavr.bardiani.domain.usecase.mb.*
import com.tolikavr.bardiani.presentation.constants.DELAY_100
import com.tolikavr.bardiani.presentation.constants.ioDispatcher
import com.tolikavr.bardiani.presentation.extensions.cycle
import com.tolikavr.bardiani.presentation.utils.CustomStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StartViewModel(
  private val application: Application,
  private val connected: Connected,
  private val connectToModBus: ConnectToModBus,
  private val setValue: SetValue,
  private val getValue: GetValue,
  private val saveDatabase: SaveDatabase,
  private val loadDatabase: LoadDatabase,
  private val updateDatabase: UpdateDatabase,
) : ViewModel() {

  init {
    viewModelScope.launch(ioDispatcher) {
      connectToModBus.execute(host = "192.168.1.38", port = "502")
    }
  }

  fun isConnected(): StateFlow<Boolean> {
    val result = MutableStateFlow(false)
    viewModelScope.launch(ioDispatcher) {
      ValueMb.connectState.collect {
        when (it) {
          is Boolean -> result.value = it
        }
      }
    }
    return result
  }

  fun setStart(set: Boolean) {
    viewModelScope.launch(ioDispatcher) {
      setValue.execute(ValueMb.start, set) { saveDb("setStart", it) }
    }
  }

  fun setValue1(set: Boolean) {
    viewModelScope.launch(ioDispatcher) {
      setValue.execute(ValueMb.valve1, set) { saveDb("setValue1", it) }
    }
  }

  fun setValue2(set: Boolean) {
    viewModelScope.launch(ioDispatcher) {
      setValue.execute(ValueMb.valve2, set) { saveDb("setValue2", it) }
    }
  }

  fun setValue3(set: Boolean) {
    viewModelScope.launch(ioDispatcher) {
      setValue.execute(ValueMb.valve3, set) { saveDb("setValue3", it) }
    }
  }


  fun getOpen(): StateFlow<Boolean> {
    val result = MutableStateFlow(false)
    viewModelScope.launch(ioDispatcher) {
      ValueMb.openState.collect {
        when (it) {
          is Boolean -> result.value = it
        }
      }
    }
    return result
  }

  fun getClose(): StateFlow<Boolean> {
    val result = MutableStateFlow(false)
    viewModelScope.launch(ioDispatcher) {
      ValueMb.closeState.collect {
        when (it) {
          is Boolean -> result.value = it
        }
      }
    }
    return result
  }

  fun getModeAuto(): StateFlow<Boolean> {
    val result = MutableStateFlow(false)
    viewModelScope.launch(ioDispatcher) {
      ValueMb.modeAutoState.collect {
        when (it) {
          is Boolean -> result.value = it
        }
      }
    }
    return result
  }

  fun getModeManual(): StateFlow<Boolean> {
    val result = MutableStateFlow(false)
    viewModelScope.launch(ioDispatcher) {
      ValueMb.modeManualState.collect {
        when (it) {
          is Boolean -> result.value = it
        }
      }
    }
    return result
  }

  val customStateFlow = CustomStateFlow<Boolean>()

  fun getModeAutoTest() {
    viewModelScope.launch(ioDispatcher) {
      ValueMb.modeAutoState.collect {
        when (it) {
          is Boolean -> customStateFlow.add(it)
        }
      }
    }
  }

  fun getModeManualTest() {
    viewModelScope.launch(ioDispatcher) {
      ValueMb.modeManualState.collect {
        when (it) {
          is Boolean -> {
            customStateFlow.add(it)
          }
        }
      }
    }
  }

  fun setTime5(set: Number) {
    viewModelScope.launch(ioDispatcher) {
      setValue.execute(ValueMb.time5, set) { saveDb("setModeManual", it) }
    }
  }

  fun setTime1(set: Number) {
    viewModelScope.launch(ioDispatcher) {
      setValue.execute(ValueMb.time1, set) { saveDb("setModeManual", it) }
    }
  }

  fun update100(): Job {
    return viewModelScope.cycle(delay = DELAY_100) {
      ValueMb.connectState.value = connected.execute()
      ValueMb.startState.value = getValue.execute(ValueMb.start) { saveDb("startState", it) }
      ValueMb.valve1State.value = getValue.execute(ValueMb.valve1) { saveDb("valve1State", it) }
      ValueMb.valve2State.value = getValue.execute(ValueMb.valve2) { saveDb("valve2State", it) }
      ValueMb.valve3State.value = getValue.execute(ValueMb.valve3) { saveDb("valve3State", it) }
      ValueMb.openState.value = getValue.execute(ValueMb.open) { saveDb("openState", it) }
      ValueMb.closeState.value = getValue.execute(ValueMb.close) { saveDb("closeState", it) }
      ValueMb.modeAutoState.value = getValue.execute(ValueMb.modeAuto) { saveDb("modeAutoState", it) }
      ValueMb.modeManualState.value = getValue.execute(ValueMb.modeManual) { saveDb("modeManualState", it) }
      ValueMb.time5State.value = getValue.execute(ValueMb.time5) { saveDb("time5State", it) }
      ValueMb.time1State.value = getValue.execute(ValueMb.time1) { saveDb("time1State", it) }
    }
  }

  private fun saveDb(name: String, id: Byte) {
    viewModelScope.launch(ioDispatcher) {
      //saveDatabase.execute(MbErrDomain(name = name, message = saveMessage(id.toInt())))
    }
  }

  private fun saveMessage(id: Int): String {
    return when (id) {
      1 -> application.baseContext.resources.getString(R.string.err_illegal_function)
      2 -> application.baseContext.resources.getString(R.string.err_illegal_data_address)
      3 -> application.baseContext.resources.getString(R.string.err_illegal_data_value)
      4 -> application.baseContext.resources.getString(R.string.err_server_device_failure)
      5 -> application.baseContext.resources.getString(R.string.err_acknowledge)
      6 -> application.baseContext.resources.getString(R.string.err_server_device_busy)
      7 -> application.baseContext.resources.getString(R.string.err_server_device_busy)
      8 -> application.baseContext.resources.getString(R.string.err_memory_parity_error)
      9 -> application.baseContext.resources.getString(R.string.err_server_device_busy)
      10 -> "Gateway path unavailable"
      11 -> "Gateway target device failed to respond"
      else -> "Unknown exception code: $id"
    }
  }

}