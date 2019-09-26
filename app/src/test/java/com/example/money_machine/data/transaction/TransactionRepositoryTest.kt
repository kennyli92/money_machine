package com.example.money_machine.data.transaction

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TransactionRepositoryTest {
  @Before
  fun before() {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
  }

  @After
  fun after() {
    RxAndroidPlugins.reset()
    RxJavaPlugins.reset()
  }

  val transactionDao: TransactionDao = mock()

  private fun transactionRepository(): TransactionRepository {
    return TransactionRepository(
      transactionDao = transactionDao
    )
  }

  @Test
  fun insert_on_success() {
    val transactionRepository = transactionRepository()
    val transaction = Transaction(userId = "userId")
    whenever(transactionDao.insert(transaction)).doReturn(Single.just(1L))

    transactionRepository.insert(transaction = transaction).test()
      .assertComplete()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValueAt(0, InsertTransactionResponse.Success)
  }

  @Test
  fun insert_on_error() {
    val transactionRepository = transactionRepository()
    val transaction = Transaction(userId = "userId")
    whenever(transactionDao.insert(transaction))
      .doReturn(Single.error(IOException("DB messed up")))

    transactionRepository.insert(transaction = transaction).test()
      .assertComplete()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValueAt(0, InsertTransactionResponse.Error(message = "DB messed up"))
  }

  @Test
  fun get_transactions_by_type_on_non_empty_query_result() {
    val transactionRepository = transactionRepository()
    val transactions = listOf(Transaction(userId = "userId"))
    whenever(transactionDao.getTransactionsByType(isSpending = true))
      .doReturn(Flowable.just(transactions))

    transactionRepository.getTransactionsByType(isSpending = true).test()
      .assertComplete()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValueAt(0, GetTransactionsResponse.Success(transactions = transactions))
  }

  @Test
  fun get_transactions_by_type_on_empty_query_result() {
    val transactionRepository = transactionRepository()

    whenever(transactionDao.getTransactionsByType(isSpending = true))
      .doReturn(Flowable.just(emptyList()))

    transactionRepository.getTransactionsByType(isSpending = true).test()
      .assertComplete()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValueAt(0, GetTransactionsResponse.NoTransactions)
  }

  @Test
  fun get_transactions_by_type_on_error() {
    val transactionRepository = transactionRepository()

    whenever(transactionDao.getTransactionsByType(isSpending = true))
      .doReturn(Flowable.error(IOException("DB messed up")))

    transactionRepository.getTransactionsByType(isSpending = true).test()
      .assertComplete()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValueAt(0, GetTransactionsResponse.Error(message = "DB messed up"))
  }
}