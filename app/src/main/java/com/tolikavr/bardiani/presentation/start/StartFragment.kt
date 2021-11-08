package com.tolikavr.bardiani.presentation.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.tolikavr.bardiani.R
import com.tolikavr.bardiani.databinding.StartFragmentBinding
import com.tolikavr.bardiani.presentation.extensions.getValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartFragment : Fragment() {

  private val viewModel by viewModel<StartViewModel>()

  private lateinit var binding: StartFragmentBinding
  private var job10: Job? = null
  private var job100: Job? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = StartFragmentBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onStart() {
    super.onStart()
    job10 = viewModel.update10()
  }

  override fun onStop() {
    super.onStop()
    job10?.cancel()
  }

  private fun descriptionStatus(
    @ColorRes color: Int,
    @StringRes textDescription: Int,
    @StringRes textSubject: Int,
  ) {
    binding.tvDescription.apply {
      setTextColor(resources.getColor(color, requireActivity().theme))
      setText(textDescription)
    }

    binding.tvSubject.apply {
      setTextColor(resources.getColor(color, requireActivity().theme))
      setText(textSubject)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    lifecycleScope.getValue(this, Lifecycle.State.STARTED) {
      viewModel.isConnected().collect {
        if (it) {
          job100 = viewModel.update100()
          descriptionStatus(
            color = R.color.gray_dark, textDescription = R.string.modeAuto, textSubject = R.string.valveClose
          )
        } else {
          job100?.cancel()
          descriptionStatus(
            color = R.color.red, textDescription = R.string.not_connected, textSubject = R.string.error_connection,
          )
          binding.btnValve1.visibility = View.GONE
          binding.btnValve2.visibility = View.INVISIBLE
          binding.btnValve3.visibility = View.GONE
        }
      }
    }

    lifecycleScope.getValue(this, Lifecycle.State.STARTED) {
      viewModel.getModelMb().collect {

        mode(it.start, it.modeAuto, it.modeManual) {
          modeIndicator(
            open = it.open,
            close = it.close,
            valve1 = it.valve1,
            valve2 = it.valve2,
            valve3 = it.valve3
          )
        }


      }
    }

    binding.btnValve1.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        viewModel.setValue1(true)
      } else {
        viewModel.setValue1(false)
      }
    }

    binding.btnValve2.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        viewModel.setValue2(true)
      } else {
        viewModel.setValue2(false)
      }
    }

    binding.btnValve3.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        viewModel.setValue3(true)
      } else {
        viewModel.setValue3(false)
      }
    }

  }

  private fun mode(
    start: Boolean,
    auto: Boolean,
    manual: Boolean,
    block: () -> Unit
  ) {
    when {
      auto && !manual -> {
        descriptionStatus(R.color.gray_dark, R.string.modeAuto, R.string.valve2_close)
        binding.btnValve1.visibility = View.GONE
        binding.btnValve2.visibility = View.VISIBLE
        if (start) binding.btnValve2.setText(R.string.stop)
        else binding.btnValve2.setText(R.string.start)
//        binding.btnValve2.textSize = 20f
        binding.btnValve3.visibility = View.GONE

      }
      !auto && manual -> {
        descriptionStatus(R.color.gray_dark, R.string.modeManual, R.string.valve2_close)
        binding.btnValve1.visibility = View.VISIBLE
        binding.btnValve2.visibility = View.VISIBLE
//        binding.btnValve2.textSize = 11f
        binding.btnValve3.visibility = View.VISIBLE
      }
      else -> {
        descriptionStatus(R.color.gray_dark, R.string.modeOff, R.string.valve2_close)
        binding.btnValve1.visibility = View.GONE
        binding.btnValve2.visibility = View.INVISIBLE
        binding.btnValve3.visibility = View.GONE
      }
    }
    block()
  }

  private fun modeIndicator(open: Boolean, close: Boolean, valve1: Boolean, valve2: Boolean, valve3: Boolean) {
    when {
      open and !close and !valve2 and !valve3 -> {
        binding.tvSubject.setText(R.string.valveOpen)
        binding.ivValve.setImageResource(R.drawable.valve2_open)
        binding.ivAir3.visibility = View.VISIBLE
      }
      !open and close and !valve2 and !valve3 -> {
        binding.tvSubject.setText(R.string.valveClose)
        binding.ivValve.setImageResource(R.drawable.valve1_close)
        invalidateAirImage()
      }
      valve2 and !valve1 and !valve3 and !open and !close -> {
        binding.tvSubject.setText(R.string.valveTopSeatFlush)
        binding.ivValve.setImageResource(R.drawable.valve3_top_seat_flush)
        binding.ivAir1.visibility = View.VISIBLE
      }
      valve3 and !valve1 and !valve2 and !open and !close -> {
        binding.tvSubject.setText(R.string.valveLowerSeatFlush)
        binding.ivValve.setImageResource(R.drawable.valve4_lower_seat_flush)
        binding.ivAir1.visibility = View.VISIBLE
      }
      else -> {
        binding.tvSubject.setText(R.string.valveClose)
        binding.ivValve.setImageResource(R.drawable.valve1_close)
        invalidateAirImage()
      }
    }
  }

  private fun invalidateAirImage() {
    binding.ivAir1.visibility = View.GONE
    binding.ivAir2.visibility = View.GONE
    binding.ivAir3.visibility = View.GONE
  }

  private suspend fun stateButton1(valve: Boolean, valve1: Button, valve2: Button, valve3: Button, mode: Boolean) {
    valve1.isEnabled = false
    valve2.isEnabled = false
    valve3.isEnabled = false
    delay(1000)
    valve1.isEnabled = true
    if (!valve) {
      valve2.isEnabled = true
      valve3.isEnabled = true
    }

  }

}
