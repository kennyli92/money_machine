package com.example.money_machine.data.transaction

data class TransactionState(
  val transactions: List<Transaction> = emptyList()
)