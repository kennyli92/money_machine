package com.example.money_machine.util

import timber.log.Timber

class Logger {
  companion object {
    fun logError(): (Throwable) -> Unit {
      return {
        Timber.e(it)
      }
    }

    fun logErrorAndThrow(): (Throwable) -> Unit {
      return {
        Timber.e(it)
        throw RuntimeException(it)
      }
    }

    fun ignoreError(): (Any) -> Unit {
      return {
        //noop
      }
    }
  }
}