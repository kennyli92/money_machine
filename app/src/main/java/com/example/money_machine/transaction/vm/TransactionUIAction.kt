package com.example.money_machine.transaction.vm

sealed class TransactionUIAction {
  object Load : TransactionUIAction()
}