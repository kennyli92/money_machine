package com.example.money_machine.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ResetDependencyOnDestroy<T : Any> {
  private var dependencyValue: T? = null

  operator fun provideDelegate(thisRef: Fragment, property: KProperty<*>):
    ReadWriteProperty<Fragment, T> {
    thisRef.lifecycle.addObserver(object : LifecycleObserver {
      @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
      fun destroyDependency() {
        if (thisRef.lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED)) {
          dependencyValue = null
        }
      }
    })

    return object : ReadWriteProperty<Fragment, T> {
      override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return dependencyValue ?: throw UninitializedPropertyAccessException(property.name)
      }

      override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        dependencyValue = value
      }
    }
  }
}