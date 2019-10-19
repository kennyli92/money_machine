package com.example.money_machine.dagger.component

import android.app.Application
import com.example.money_machine.dagger.module.ActivityModule
import com.example.money_machine.dagger.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
  fun inject(app: Application)
  fun activityComponent(activityModule: ActivityModule): ActivityComponent
}