package com.tolikavr.bardiani.presentation.di.mb

import com.tolikavr.bardiani.presentation.start.StartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mbAppModule = module {
  viewModel<StartViewModel> {
    StartViewModel(
      application = get(),
      connected = get(),
      connectToModBus = get(),
      setValue = get(),
      getValue = get(),
      saveDatabase = get(),
      loadDatabase = get(),
      updateDatabase = get(),
    )
  }
}