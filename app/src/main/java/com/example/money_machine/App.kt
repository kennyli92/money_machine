package com.example.money_machine

import android.app.Application
import com.example.money_machine.dagger.AppInjector
import com.example.money_machine.dagger.Injector

class App : Application(), Injector by AppInjector() {
  override fun onCreate() {
    super.onCreate()
    initializeAppComponent(app = this)
  }
}