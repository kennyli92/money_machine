package com.example.money_machine.db.transaction

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TransactionDao {
  @Insert
  fun insert(transaction: Transaction)
}