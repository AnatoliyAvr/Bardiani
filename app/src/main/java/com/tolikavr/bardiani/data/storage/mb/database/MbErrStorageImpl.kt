package com.tolikavr.bardiani.data.storage.mb.database

import com.tolikavr.bardiani.data.storage.mb.database.model.MbErrRoom
import com.tolikavr.bardiani.data.db.mb.MbErrDao

class MbErrStorageImpl(
  private val mbErrDao: MbErrDao
) : MbErrStorage {

  override suspend fun saveDatabase(mbErrRoom: MbErrRoom) {
    mbErrDao.saveErr(mbErrRoom)
  }

  override suspend fun loadDatabase(): List<MbErrRoom> {
    return mbErrDao.loadErr()
  }

  override suspend fun updateDatabase(id: Byte) {
    mbErrDao.updateErr(id)
  }
}