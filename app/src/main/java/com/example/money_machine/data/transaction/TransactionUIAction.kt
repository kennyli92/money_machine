package com.example.money_machine.data.transaction

sealed class TransactionUIAction {
  object Load : TransactionUIAction()
}