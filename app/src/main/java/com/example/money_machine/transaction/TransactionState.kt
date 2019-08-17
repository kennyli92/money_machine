package com.example.money_machine.transaction

import com.example.money_machine.data.transaction.Transaction

data class TransactionState(
  val transactions: List<Transaction> = emptyList()
)