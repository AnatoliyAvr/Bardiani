package com.tolikavr.bardiani.data.db.mb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tolikavr.bardiani.data.storage.mb.database.model.MbErrRoom

@Database(entities = [MbErrRoom::class], version = 1, exportSchema = false)
abstract class MbErrDatabase : RoomDatabase() {
  abstract fun mbErrDao(): MbErrDao

  companion object {
    @Volatile
    private var INSTANCE: MbErrDatabase? = null

    fun getDatabase(context: Context): MbErrDatabase {

      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context,
          MbErrDatabase::class.java,
          "mb_err"
        ).fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
//         return instance
        instance
      }
    }
  }
}