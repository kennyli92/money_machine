package com.example.money_machine.db.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TransactionDao {
  // create new transaction record. If record already exist, replace it
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(transaction: Transaction): Completable

  // get all transaction by type: spending or saving
  @Query("SELECT * FROM `transaction` WHERE isSpending = :isSpending")
  fun getTransactionsByType(isSpending: Boolean): Flowable<Transaction>
}