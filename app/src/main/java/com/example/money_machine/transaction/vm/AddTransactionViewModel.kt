package com.example.money_machine.transaction.vm

import androidx.lifecycle.ViewModel
import com.example.money_machine.data.transaction.InsertTransactionResponse
import com.example.money_machine.data.transaction.Transaction
import com.example.money_machine.data.transaction.TransactionRepository
import com.example.money_machine.extensions.plusAssign
import com.example.money_machine.util.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class AddTransactionViewModel(
  private val transactionRepository: TransactionRepository
) : ViewModel() {
  private val disposables: CompositeDisposable = CompositeDisposable()
  private val singleEventObs = PublishSubject.create<AddTransactionSingleEvent>()

  fun isTransactionInserted(): Observable<Boolean> {
    return singleEventObs.map { it.isTransactionInserted }
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun uiActionHandler(actionObs: Observable<AddTransactionUIAction>) {
    disposables += actionObs
      .observeOn(Schedulers.computation())
      .flatMap {
        when (it) {
          is AddTransactionUIAction.InsertTransaction -> onInsertTransactionAction(transaction = it.transaction)
        }
      }.subscribe({
        singleEventObs.onNext(it)
      }, Logger.logErrorAndThrow())
  }

  private fun onInsertTransactionAction(transaction: Transaction): Observable<AddTransactionSingleEvent> {
    return transactionRepository.insert(transaction = transaction)
      .subscribeOn(Schedulers.computation())
      .map {
        when (it) {
          InsertTransactionResponse.Success -> AddTransactionSingleEvent(isTransactionInserted = true)
          // add logic to display error dialog
          is InsertTransactionResponse.Error -> AddTransactionSingleEvent()
        }
      }
  }

  override fun onCleared() {
    disposables.dispose()
    super.onCleared()
  }
}
