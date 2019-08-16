package com.example.money_machine.data.transaction

import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
  private val transactionDao: TransactionDao
) {
  fun insert(transaction: Transaction): Completable {
    return transactionDao.insert(transaction = transaction)
  }

  fun getTransactionsByType(isSpending: Boolean): Flowable<Transaction> {
    return transactionDao.getTransactionsByType(isSpending = isSpending)
  }
}