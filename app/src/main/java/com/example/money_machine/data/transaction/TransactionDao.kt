package com.example.money_machine.data.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TransactionDao {
  // create new transaction record. If record already exist, replace it
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(transaction: Transaction): Single<Long>

  // get all transaction by type: spending or saving
  @Query("SELECT * FROM `transaction` WHERE isSpending = :isSpending")
  fun getTransactionsByType(isSpending: Boolean): Flowable<List<Transaction>>
}