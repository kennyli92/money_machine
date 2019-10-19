package com.example.money_machine.dagger

import com.example.money_machine.App
import com.example.money_machine.MainActivity
import com.example.money_machine.dagger.component.ActivityComponent
import com.example.money_machine.dagger.component.AppComponent

interface Injector {
  /**
   * This will always be called in [App]
   */
  fun initializeAppComponent(app: App)

  val appComponent: AppComponent

  fun activityComponent(activity: MainActivity): ActivityComponent
}