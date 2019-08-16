package com.smshift.smshift.dagger

import android.app.Application
import com.smshift.smshift.controller.Activity
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

    fun getActivityComponent(activity: Activity): ActivityComponent {
      return getAppComponent(activity.application)
        .activityComponent(ActivityModule(activity = activity))
    }

    fun getBottomNavigationComponent(activity: Activity): BottomNavigationComponent {
      return getActivityComponent(activity).bottomNavigationComponent()
    }

    fun getContactComponent(activity: Activity): ContactComponent {
      return getActivityComponent(activity).contactComponent()
    }
  }
}