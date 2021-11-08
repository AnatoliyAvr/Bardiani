package com.tolikavr.bardiani.presentation.start

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolikavr.bardiani.R
import com.tolikavr.bardiani.domain.usecase.mb.*
import com.tolikavr.bardiani.presentation.constants.DELAY_10
import com.tolikavr.bardiani.presentation.constants.DELAY_100
import com.tolikavr.bardiani.presentation.constants.ioDispatcher
import com.tolikavr.bardiani.presentation.extensions.cycle
import com.tolikavr.bardiani.presentation.start.models.ModelMb
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
//      connectToModBus.execute(host = "192.168.1.38", port = "502")
      connectToModBus.execute(host = "10.12.18.106", port = "503")
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

  fun getModelMb(): StateFlow<ModelMb> {
    val result = MutableStateFlow(ModelMb())
    viewModelScope.launch(ioDispatcher) {
      ValueMb.modelMbState.collect {
        when (it) {
          is ModelMb -> result.value = it
        }
      }
    }
    return result
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

  fun update10(): Job {
    return viewModelScope.cycle(delay = DELAY_10) {
      ValueMb.connectState.value = connected.execute()
    }
  }

  fun update100(): Job {
    return viewModelScope.cycle(delay = DELAY_100) {
      try {
        ValueMb.modelMbState.value = ModelMb(
          start = getValue.execute(ValueMb.start) {},
          valve1 = getValue.execute(ValueMb.valve1) {},
          valve2 = getValue.execute(ValueMb.valve2) {},
          valve3 = getValue.execute(ValueMb.valve3) {},
          open = getValue.execute(ValueMb.open) {},
          close = getValue.execute(ValueMb.close) {},
          modeAuto = getValue.execute(ValueMb.modeAuto) {},
          modeManual = getValue.execute(ValueMb.modeManual) {},
          time5 = getValue.execute(ValueMb.time5) {},
          time1 = getValue.execute(ValueMb.time1) {},
        )
      } catch (e: Exception) {
        Log.d("AAA", "${e.message}")
      }

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