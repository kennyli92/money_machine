package com.example.money_machine.dagger

import android.content.Context
import com.example.money_machine.db.RoomDb
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
}