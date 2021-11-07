package com.tolikavr.bardiani.data.db.mb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tolikavr.bardiani.data.storage.mb.database.model.MbErrRoom

@Dao
interface MbErrDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveErr(mbErrRoom: MbErrRoom)

  @Query("SELECT * FROM mb_err WHERE check_err=0")
  suspend fun loadErr(): List<MbErrRoom>

  @Query("UPDATE mb_err SET check_err=1 WHERE id =:id")
  suspend fun updateErr(id: Byte)

//  @Update
//  suspend fun saveErr(mbErrRoom: MbErrRoom)
}