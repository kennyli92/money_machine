package com.example.money_machine.dagger

import android.app.Application
import com.example.money_machine.MainActivity
import com.smshift.smshift.extensions.requireNotNull


class Injector private constructor() {
  companion object {
    private var appComponent: AppComponent? = null

    fun getAppComponent(application: Application): AppComponent {
      if (appComponent == null) {
        appComponent =
          DaggerAppComponent
            .builder()
            .appModule(AppModule(application = application))
            .build()
      }

      return appComponent.requireNotNull()
    }

    fun getActivityComponent(activity: MainActivity): ActivityComponent {
      return getAppComponent(activity.application)
        .activityComponent(ActivityModule(activity = activity))
    }
  }
}