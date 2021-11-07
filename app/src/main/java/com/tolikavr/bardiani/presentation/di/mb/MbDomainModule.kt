package com.tolikavr.bardiani.presentation.di.mb

import com.tolikavr.bardiani.data.repository.MbErrRepositoryImpl
import com.tolikavr.bardiani.data.storage.mb.database.MbErrStorageImpl
import com.tolikavr.bardiani.domain.usecase.mb.*
import org.koin.dsl.module

val mbDomainModule = module {
  factory { Connected(mbRepository = get()) }
  factory { ConnectToModBus(mbRepository = get()) }
  factory { SetValue(mbRepository = get()) }
  factory { GetValue(mbRepository = get()) }
  factory { MbErrStorageImpl(mbErrDao = get()) }
  factory { MbErrRepositoryImpl(mbErrStorage = get()) }
  factory { SaveDatabase(mbErrRepository = get()) }
  factory { LoadDatabase(mbErrRepository = get()) }
  factory { UpdateDatabase(mbErrRepository = get()) }
}

