package com.example.money_machine.dagger.module

import android.content.Context
import androidx.appcompat.app.ActionBar
import com.example.money_machine.MainActivity
import com.example.money_machine.dagger.ActivityContext
import com.example.money_machine.dagger.scope.ActivityScope
import com.example.money_machine.extensions.requireNotNull
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main.*

@Module
class ActivityModule(val activity: MainActivity) {

  @Provides
  @ActivityScope
  fun providesActionBar(): ActionBar {
    return activity.supportActionBar.requireNotNull()
  }

  @Provides
  @ActivityScope
  fun providesBottomNavigation(): BottomNavigationView {
    return activity.bottom_navigation
  }

  @ActivityContext
  @Provides
  @ActivityScope
  fun providesActivityContext(): Context {
    return activity
  }
}
