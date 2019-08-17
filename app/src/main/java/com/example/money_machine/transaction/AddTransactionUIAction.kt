package com.example.money_machine.transaction

import com.example.money_machine.data.transaction.Transaction

sealed class AddTransactionUIAction {
  data class InsertTransaction(
    val transaction: Transaction
  ): AddTransactionUIAction()
}