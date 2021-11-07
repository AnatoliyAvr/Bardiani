package com.tolikavr.bardiani.data.storage.mb.database

import com.tolikavr.bardiani.data.storage.mb.database.model.MbErrRoom

interface MbErrStorage {

  suspend fun saveDatabase(mbErrRoom: MbErrRoom)

  suspend fun loadDatabase(): List<MbErrRoom>

  suspend fun updateDatabase(id: Byte)
}