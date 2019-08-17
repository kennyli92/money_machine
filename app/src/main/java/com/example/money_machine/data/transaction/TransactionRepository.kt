package com.example.money_machine.data.transaction

import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
  private val transactionDao: TransactionDao
) {
  // Return encapsulated response of Insert transaction method. Can be updated to return different kinds of response
  fun insert(transaction: Transaction): Observable<InsertTransactionResponse> {
    return transactionDao.insert(transaction = transaction)
      .andThen(Observable.just(InsertTransactionResponse.Success as InsertTransactionResponse))
  }

  // Return encapsulated response of Get transaction method. Can be updated to return different kinds of response
  fun getTransactionsByType(isSpending: Boolean): Flowable<GetTransactionsResponse> {
    return transactionDao.getTransactionsByType(isSpending = isSpending)
      .flatMap {
        if (it.isNotEmpty()) {
          Flowable.just(GetTransactionsResponse.Success(transactions = it))
        } else {
          Flowable.just(GetTransactionsResponse.NoTransactions)
        }
      }
      .onErrorReturn {
        GetTransactionsResponse.Error(message = it.message ?: "Unknown Error")
      }
  }
}