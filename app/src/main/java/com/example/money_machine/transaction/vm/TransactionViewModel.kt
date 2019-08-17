package com.example.money_machine.transaction.vm

import androidx.lifecycle.ViewModel
import com.example.money_machine.StateTransition
import com.example.money_machine.util.Logger
import com.smshift.smshift.extensions.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class TransactionViewModel : ViewModel() {
  private var state: TransactionState =
    TransactionState()
  private val disposables: CompositeDisposable = CompositeDisposable()
  private val stateObs = BehaviorSubject.create<TransactionState>()

  fun uiActionHandler(actionObs: Observable<TransactionUIAction>) {
    disposables += actionObs
      .flatMap {
        when (it) {
          TransactionUIAction.Load -> onLoadAction()
        }
      }.subscribe({
        state = it(state)
        stateObs.onNext(state)
      }, Logger.logErrorAndThrow())
  }

  private fun onLoadAction(): Observable<StateTransition<TransactionState>> {
    // TODO update to call TransactionRepository
    return Observable.just({state: TransactionState -> state})
  }

  override fun onCleared() {
    disposables.dispose()
    super.onCleared()
  }
}
