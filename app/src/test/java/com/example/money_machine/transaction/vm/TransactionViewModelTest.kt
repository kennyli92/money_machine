package com.example.money_machine.transaction.vm

import com.example.money_machine.R
import com.example.money_machine.data.transaction.GetTransactionsResponse
import com.example.money_machine.data.transaction.Transaction
import com.example.money_machine.data.transaction.TransactionRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test

class TransactionViewModelTest {
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

  private val transactionType: Int = R.string.transaction_spending
  private val transactionRepository: TransactionRepository = mock()

  private fun transactionViewModel(): TransactionViewModel {
    return TransactionViewModel(
      transactionType = transactionType,
      transactionRepository = transactionRepository
    )
  }

  @Test
  fun load_transaction_when_success() {
    val transactionViewModel = transactionViewModel()
    val actionSignal = PublishSubject.create<TransactionUIAction>()
    transactionViewModel.uiActionHandler(actionObs = actionSignal)

    val transactions = listOf(Transaction(userId = "userId"))
    whenever(transactionRepository.getTransactionsByType(isSpending = true))
      .doReturn(Flowable.just(
        GetTransactionsResponse.Success(transactions = transactions
        ) as GetTransactionsResponse)
      )

    actionSignal.onNext(TransactionUIAction.Load)

    transactionViewModel.stateObs().test()
      .assertNotComplete()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValueAt(0, TransactionState(transactions = transactions))
  }

  @Test
  fun load_transaction_when_no_transactions() {
    val transactionViewModel = transactionViewModel()
    val actionSignal = PublishSubject.create<TransactionUIAction>()
    transactionViewModel.uiActionHandler(actionObs = actionSignal)

    whenever(transactionRepository.getTransactionsByType(isSpending = true))
      .doReturn(Flowable.just(
        GetTransactionsResponse.NoTransactions as GetTransactionsResponse)
      )

    actionSignal.onNext(TransactionUIAction.Load)

    transactionViewModel.stateObs().test()
      .assertNotComplete()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValueAt(0, TransactionState())
  }

  @Test
  fun load_transaction_when_error() {
    val transactionViewModel = transactionViewModel()
    val actionSignal = PublishSubject.create<TransactionUIAction>()
    transactionViewModel.uiActionHandler(actionObs = actionSignal)

    whenever(transactionRepository.getTransactionsByType(isSpending = true))
      .doReturn(Flowable.just(
        GetTransactionsResponse.Error(message = "DB messed up") as GetTransactionsResponse)
      )

    actionSignal.onNext(TransactionUIAction.Load)

    transactionViewModel.stateObs().test()
      .assertNotComplete()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValueAt(0, TransactionState())
  }
}