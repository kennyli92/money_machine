package com.example.money_machine.transaction

import androidx.lifecycle.ViewModel
import com.example.money_machine.util.Logger
import com.smshift.smshift.extensions.plusAssign
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class AddTransactionViewModel : ViewModel() {
  private val disposables: CompositeDisposable = CompositeDisposable()
  private val singleEventObs = PublishSubject.create<AddTransactionSingleEvent>()

  fun isTransactionInserted(): Observable<Boolean> {
    return singleEventObs.map { it.isTransactionInserted }
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun uiActionHandler(actionObs: Observable<AddTransactionUIAction>) {
    disposables += actionObs
      .flatMap {
        when (it) {
          is AddTransactionUIAction.InsertTransaction -> onInsertTransactionAction()
        }
      }.subscribe({
        singleEventObs.onNext(it)
      }, Logger.logErrorAndThrow())
  }

  private fun onInsertTransactionAction(): Observable<AddTransactionSingleEvent> {
    // TODO update to call TransactionRepository
    return Observable.just(AddTransactionSingleEvent())
  }

  override fun onCleared() {
    disposables.dispose()
    super.onCleared()
  }
}
