package com.tolikavr.bardiani.domain.repository

import com.tolikavr.bardiani.domain.models.MbErrDomain


interface MbErrRepository {

  suspend fun saveDatabase(mbErrDomain: MbErrDomain)

  suspend fun loadDatabase(): List<MbErrDomain>

  suspend fun updateDatabase(id: Byte)

}