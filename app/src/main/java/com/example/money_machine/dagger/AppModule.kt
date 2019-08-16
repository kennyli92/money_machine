package com.example.money_machine.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DbModule::class])
class AppModule(private val application: Application) {

  @Singleton
  @Provides
  fun providesContext(): Context {
    return application.applicationContext
  }
}