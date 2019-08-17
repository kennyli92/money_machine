package com.example.money_machine.transaction

sealed class TransactionUIAction {
  object Load : TransactionUIAction()
}