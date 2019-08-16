package com.example.money_machine.dagger

import android.content.Context
import com.example.money_machine.data.RoomDb
import com.example.money_machine.data.transaction.TransactionDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
  @Singleton
  @Provides
  fun providesRoomDb(applicationContext: Context): RoomDb {
    return RoomDb.getInstance(applicationContext)
  }

  @Singleton
  @Provides
  fun providesTransactionDao(db: RoomDb): TransactionDao {
    return db.transactionDao()
  }
}