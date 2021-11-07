package com.tolikavr.bardiani.presentation.start

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.tolikavr.bardiani.R
import com.tolikavr.bardiani.databinding.StartFragmentBinding
import com.tolikavr.bardiani.presentation.extensions.getValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartFragment : Fragment() {

  private val viewModel by viewModel<StartViewModel>()

  private lateinit var binding: StartFragmentBinding
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
    job100 = viewModel.update100()
  }

  override fun onStop() {
    super.onStop()
    job100?.cancel()
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
          descriptionStatus(
            color = R.color.gray_dark, textDescription = R.string.modeAuto, textSubject = R.string.valveClose
          )
        } else {
          descriptionStatus(
            color = R.color.red, textDescription = R.string.not_connected, textSubject = R.string.error_connection,
          )
          binding.btnValve1.visibility = View.GONE
          binding.btnValve2.visibility = View.INVISIBLE
          binding.btnValve3.visibility = View.GONE
        }
      }


      viewModel.getModeManual().collect {
        if (it) manual()
        else disable()
      }

    }

    lifecycleScope.getValue(this, Lifecycle.State.STARTED) {
      viewModel.getModeAutoTest()
    }

    lifecycleScope.getValue(this, Lifecycle.State.STARTED) {
      viewModel.getModeManualTest()
    }

    lifecycleScope.getValue(this, Lifecycle.State.STARTED) {
      viewModel.customStateFlow.custom.collect {
        if (it[0]) auto()
        if (it[1]) manual()
        if ((it[0] && it[1]) || (!it[0] && !it[1])) disable()
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

  private fun auto() {
    Log.d("AAA", "auto")
  }

  private fun manual() {
    Log.d("AAA", "maual")
  }

  private fun disable() {
    Log.d("AAA", "disabled")
  }


}