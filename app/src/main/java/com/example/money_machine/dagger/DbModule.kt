package com.smshift.smshift.dagger

import android.content.Context
import com.smshift.smshift.db.RoomDb
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