package com.tolikavr.bardiani.data.storage.mb.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mb_err")
data class MbErrRoom(
  @PrimaryKey(autoGenerate = true)
  val id: Byte = 0,
  val name: String,
  val date: Long = 0,
  val message: String? = null,
  val check_err: Boolean = false
)
