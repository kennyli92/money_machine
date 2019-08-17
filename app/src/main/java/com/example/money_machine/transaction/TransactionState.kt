package com.example.money_machine.transaction

import com.example.money_machine.data.transaction.Transaction

// used to store Transaction state. This will be persisted in TransactionViewModel
data class TransactionState(
  val transactions: List<Transaction> = emptyList()
)