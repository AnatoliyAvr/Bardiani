package com.tolikavr.bardiani.data.repository

import com.tolikavr.bardiani.data.storage.mb.database.MbErrStorage
import com.tolikavr.bardiani.data.storage.mb.database.model.MbErrRoom
import com.tolikavr.bardiani.domain.models.MbErrDomain
import com.tolikavr.bardiani.domain.repository.MbErrRepository
import java.util.*

class MbErrRepositoryImpl(private val mbErrStorage: MbErrStorage) : MbErrRepository {

  override suspend fun saveDatabase(mbErrDomain: MbErrDomain) {
    mbErrStorage.saveDatabase(mapToStorage(mbErrDomain))
  }

  override suspend fun loadDatabase(): List<MbErrDomain> {
    return mapToDomain(mbErrStorage.loadDatabase())
  }

  override suspend fun updateDatabase(id: Byte) {
    mbErrStorage.updateDatabase(id)
  }

  private fun mapToStorage(mbErrDomain: MbErrDomain): MbErrRoom {
    return MbErrRoom(
      id = mbErrDomain.id,
      name = mbErrDomain.name,
      date = mbErrDomain.date.time,
      message = mbErrDomain.message,
      check_err = mbErrDomain.check_err
    )
  }

  private fun mapToDomain(workUsersRoom: List<MbErrRoom>): List<MbErrDomain> {
    return workUsersRoom.map {
      MbErrDomain(
        id = it.id,
        date = Date(it.date),
        message = it.message,
        check_err = it.check_err
      )
    }
  }
}