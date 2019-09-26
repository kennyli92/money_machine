package com.example.money_machine.transaction.vm

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.example.money_machine.R
import com.example.money_machine.StateTransition
import com.example.money_machine.data.transaction.GetTransactionsResponse
import com.example.money_machine.data.transaction.Transaction
import com.example.money_machine.data.transaction.TransactionRepository
import com.example.money_machine.util.Logger
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class TransactionViewModel(
  // resId of R.string.transaction_spending or R.string.transaction_saving
  private val transactionType: Int,
  private val transactionRepository: TransactionRepository
) : ViewModel() {
  @Volatile
  private var state: TransactionState = TransactionState()
  @VisibleForTesting
  internal val stateObs = BehaviorSubject.create<TransactionState>().toSerialized()

  fun stateObs(): Observable<TransactionState> {
    return stateObs.observeOn(AndroidSchedulers.mainThread())
  }

  fun transactionsObs(): Observable<List<Transaction>> {
    return stateObs.map { it.transactions }
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun uiActionHandler(actionObs: Observable<TransactionUIAction>): Disposable {
    return actionObs
      .observeOn(Schedulers.computation())
      .toFlowable(BackpressureStrategy.BUFFER)
      .flatMap {
        when (it) {
          TransactionUIAction.Load -> onLoadAction()
        }
      }.subscribe({
        state = it(state)
        stateObs.onNext(state)
      }, Logger.logErrorAndThrow())
  }

  private fun onLoadAction(): Flowable<StateTransition<TransactionState>> {
    val isSpending = when (transactionType) {
      R.string.transaction_spending -> true
      else -> false
    }
    return transactionRepository.getTransactionsByType(isSpending = isSpending)
      .subscribeOn(Schedulers.computation())
      .map {
        when (it) {
          is GetTransactionsResponse.Success -> { state: TransactionState ->
            state.copy(transactions = it.transactions)
          }
          GetTransactionsResponse.NoTransactions -> { state: TransactionState ->
            // TODO: update to send no transactionsObs error
            state.copy()
          }
          is GetTransactionsResponse.Error -> { state: TransactionState ->
            // TODO: update to send generic error
            state.copy()
          }
        }
      }
  }
}
