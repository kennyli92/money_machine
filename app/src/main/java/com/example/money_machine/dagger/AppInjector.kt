package com.example.money_machine.dagger

import com.example.money_machine.App
import com.example.money_machine.MainActivity
import com.example.money_machine.dagger.component.ActivityComponent
import com.example.money_machine.dagger.component.AppComponent
import com.example.money_machine.dagger.component.DaggerAppComponent
import com.example.money_machine.dagger.module.ActivityModule
import com.example.money_machine.dagger.module.AppModule
import com.example.money_machine.extensions.requireNotNull

class AppInjector : Injector {
  private var _appComponent: AppComponent? = null

  override fun initializeAppComponent(app: App) {
    _appComponent = DaggerAppComponent
      .builder()
      .appModule(AppModule(app = app))
      .build()
  }

  override val appComponent: AppComponent
    get() = _appComponent.requireNotNull()

  override fun activityComponent(activity: MainActivity): ActivityComponent {
    return _appComponent.requireNotNull()
      .activityComponent(activityModule = ActivityModule(activity = activity))
  }
}