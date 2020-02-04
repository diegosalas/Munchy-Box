package com.cabify.cabistore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SaleDetail::class], version = 1, exportSchema = false)
abstract class StoreDatabase : RoomDatabase() {

  abstract val storeDatabaseDao: StoreDatabaseDao

  companion object {
    @Volatile
    private var INSTANCE: StoreDatabase? = null

    fun getInstance(context: Context): StoreDatabase {
      synchronized(this) {
        var instance = INSTANCE
        if (instance == null) {
          instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoreDatabase::class.java,
                    "sales_history_database")
                .fallbackToDestructiveMigration()
              .build()
          INSTANCE = instance
        }

        return instance
      }
    }
  }
}