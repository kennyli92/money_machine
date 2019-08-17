package com.example.money_machine.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class DisposableOnLifecycleChange {
  private var disposables: CompositeDisposable? = null

  operator fun provideDelegate(thisRef: LifecycleOwner, property: KProperty<*>):
    ReadOnlyProperty<LifecycleOwner, CompositeDisposable> {
    thisRef.lifecycle.addObserver(object : LifecycleObserver {
      @OnLifecycleEvent(Lifecycle.Event.ON_START)
      fun initializeDisposable() {
        if (thisRef.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
          disposables = CompositeDisposable()
        }
      }

      @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
      fun disposeDisposables() {
        disposables?.dispose()
        disposables = null
      }
    })

    return object : ReadOnlyProperty<LifecycleOwner, CompositeDisposable> {
      override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): CompositeDisposable {
        return disposables ?: throw UninitializedPropertyAccessException(property.name)
      }
    }
  }
}