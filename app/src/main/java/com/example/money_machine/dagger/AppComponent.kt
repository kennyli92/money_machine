package com.example.money_machine.dagger

import android.app.Application
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
  fun inject(app: Application)
  fun activityComponent(activityModule: ActivityModule): ActivityComponent
}