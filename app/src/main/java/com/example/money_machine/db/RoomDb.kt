package com.smshift.smshift.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.money_machine.db.transaction.Transaction
import com.example.money_machine.db.transaction.TransactionConverters

@Database(entities = [Transaction::class], version = 1)
@TypeConverters(value = [TransactionConverters::class])
abstract class RoomDb : RoomDatabase() {
  companion object {
    private const val DB_NAME = "money_machine.db"
    @Volatile private var INSTANCE: RoomDb? = null

    fun getInstance(context: Context): RoomDb {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
      }
    }

    private fun buildDatabase(context: Context): RoomDb {
      return Room.databaseBuilder(context, RoomDb::class.java, DB_NAME).build()
    }
  }
}