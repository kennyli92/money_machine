package com.example.money_machine.transaction.vm

// used to encapsulate single event that shouldn't be persisted in the view model
data class AddTransactionSingleEvent(
  val isTransactionInserted: Boolean = false
)