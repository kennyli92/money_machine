@file:Suppress("NOTHING_TO_INLINE")

package com.example.money_machine.extensions

inline fun <T : Any> T?.requireNotNull(): T {
  return requireNotNull(this)
}

inline fun <T : Any> T?.requireNotNull(lazyMessage: () -> Any): T {
  return requireNotNull(this, lazyMessage)
}

inline fun <T : Any> T?.checkNotNull(): T {
  return checkNotNull(this)
}

inline fun <T : Any> T?.checkNotNull(lazyMessage: () -> Any): T {
  return checkNotNull(this, lazyMessage)
}

