package com.example.money_machine.transaction.vm

import com.example.money_machine.data.transaction.InsertTransactionResponse
import com.example.money_machine.data.transaction.Transaction
import com.example.money_machine.data.transaction.TransactionRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test

class AddTransactionViewModelTest {
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

  private val transactionRepository: TransactionRepository = mock()

  private fun addTransactionViewModel(): AddTransactionViewModel {
    return AddTransactionViewModel(
      transactionRepository = transactionRepository
    )
  }

  @Test
  fun insert_transaction_when_success() {
    val addTransactionViewModel = addTransactionViewModel()
    val actionSignal = PublishSubject.create<AddTransactionUIAction>()
    addTransactionViewModel.uiActionHandler(actionObs = actionSignal)

    val transaction = Transaction(userId = "userId")
    whenever(transactionRepository.insert(transaction = transaction))
      .doReturn(Single.just(InsertTransactionResponse.Success as InsertTransactionResponse))

    val testObs = addTransactionViewModel.singleEventObs().test()
    actionSignal.onNext(AddTransactionUIAction.InsertTransaction(transaction = transaction))

    testObs
      .assertNotComplete()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValueAt(0, AddTransactionSingleEvent(isTransactionInserted = true))
  }

  @Test
  fun insert_transaction_when_error() {
    val addTransactionViewModel = addTransactionViewModel()
    val actionSignal = PublishSubject.create<AddTransactionUIAction>()
    addTransactionViewModel.uiActionHandler(actionObs = actionSignal)

    val transaction = Transaction(userId = "userId")
    whenever(transactionRepository.insert(transaction = transaction))
      .doReturn(
        Single.just(InsertTransactionResponse.Error(
          message = "DB messed up"
        ) as InsertTransactionResponse)
      )

    val testObs = addTransactionViewModel.singleEventObs().test()
    actionSignal.onNext(AddTransactionUIAction.InsertTransaction(transaction = transaction))

    testObs
      .assertNotComplete()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValueAt(0, AddTransactionSingleEvent(isTransactionInserted = false))
  }

  @Test
  fun isTransactionInsertedObs() {
    val addTransactionViewModel = addTransactionViewModel()

    val testObs = addTransactionViewModel.isTransactionInsertedObs().test()
    addTransactionViewModel.singleEventObs.onNext(
      AddTransactionSingleEvent(isTransactionInserted = true))

    testObs.assertNotComplete()
      .assertNoErrors()
      .assertValueCount(1)
      .assertValueAt(0, true)
  }
}