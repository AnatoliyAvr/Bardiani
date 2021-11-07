package com.tolikavr.bardiani.presentation.di.mb

import android.app.Application
import com.tolikavr.bardiani.data.db.mb.MbErrDao
import com.tolikavr.bardiani.data.db.mb.MbErrDatabase
import com.tolikavr.bardiani.data.repository.MbErrRepositoryImpl
import com.tolikavr.bardiani.data.repository.MbRepositoryImpl
import com.tolikavr.bardiani.data.storage.mb.MbStorage
import com.tolikavr.bardiani.data.storage.mb.MbStorageImpl
import com.tolikavr.bardiani.data.storage.mb.database.MbErrStorage
import com.tolikavr.bardiani.data.storage.mb.database.MbErrStorageImpl
import com.tolikavr.bardiani.domain.repository.MbErrRepository
import com.tolikavr.bardiani.domain.repository.MbRepository
import org.koin.dsl.module

val mbDataModule = module {

  fun database(application: Application) = MbErrDatabase.getDatabase(application).mbErrDao()

  single<MbStorage> { MbStorageImpl() }
  single<MbRepository> { MbRepositoryImpl(mbStorage = get()) }
  single<MbErrStorage> { MbErrStorageImpl(mbErrDao = get()) }
  single<MbErrRepository> { MbErrRepositoryImpl(mbErrStorage = get()) }
  single<MbErrDao> { database(application = get()) }


}