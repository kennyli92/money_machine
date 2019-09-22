package com.example.money_machine.data.transaction

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.money_machine.data.RoomDb
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TransactionDaoTest {
  // needed to run tasks synchronously
  @JvmField @Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var transactionDao: TransactionDao
  private lateinit var db: RoomDb

  @Before
  fun createDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(context, RoomDb::class.java).build()
    transactionDao = db.transactionDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    db.close()
  }

  @Test
  @Throws(Exception::class)
  fun insert_spending_transaction() {
    val transaction = Transaction(
      id = "1",
      userId = "Ken",
      description = "New Clothes",
      amount = "50.00",
      tag = TransactionTag.CLOTH,
      isSpending = true
    )

    transactionDao.insert(transaction = transaction).test()
      .assertComplete()
      .assertNoErrors()

    val spendingTransactions = transactionDao.getTransactionsByType(isSpending = true).test()
      .assertNotComplete()
      .assertNoErrors()
      .values()

    Truth.assertThat(spendingTransactions).isEqualTo(listOf(listOf(transaction)))
  }

  @Test
  @Throws(Exception::class)
  fun insert_non_spending_transaction() {
    val transaction = Transaction(
      id = "2",
      userId = "Ken",
      description = "New Clothes",
      amount = "50.00",
      tag = TransactionTag.CLOTH,
      isSpending = false
    )

    transactionDao.insert(transaction = transaction).test()
      .assertComplete()
      .assertNoErrors()

    val spendingTransactions = transactionDao.getTransactionsByType(isSpending = false).test()
      .assertNotComplete()
      .assertNoErrors()
      .values()

    Truth.assertThat(spendingTransactions).isEqualTo(listOf(listOf(transaction)))
  }
}