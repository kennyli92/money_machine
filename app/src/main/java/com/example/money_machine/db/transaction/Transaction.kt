package com.example.money_machine.db.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaction")
data class Transaction(
  @PrimaryKey(autoGenerate = true) val id: Long,
  val userId: String,
  val date: Date = Date(),
  val description: String = "",
  val amount: String = "0.00",
  val tag: TransactionTag = TransactionTag.OTHER,
  val isSpending: Boolean = false
)