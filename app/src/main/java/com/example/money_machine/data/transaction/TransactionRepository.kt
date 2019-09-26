package com.example.money_machine.data.transaction

import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
  private val transactionDao: TransactionDao
) {
  // Return encapsulated response of Insert transaction method. Can be updated to return different kinds of response
  fun insert(transaction: Transaction): Single<InsertTransactionResponse> {
    return transactionDao.insert(transaction = transaction)
      .map { InsertTransactionResponse.Success as InsertTransactionResponse }
      .onErrorReturn {  throwable ->
        InsertTransactionResponse.Error(message = throwable.message ?: "Unknown Error")
      }
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
      .onErrorReturn { throwable ->
        GetTransactionsResponse.Error(message = throwable.message ?: "Unknown Error")
      }
  }
}