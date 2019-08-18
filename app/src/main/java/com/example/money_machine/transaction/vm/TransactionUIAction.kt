package com.example.money_machine.transaction.vm

// this list all possible UI actions the user can take on the Transaction screen
sealed class TransactionUIAction {
  object Load : TransactionUIAction()
}