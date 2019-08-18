package com.example.money_machine.transaction.vm

import com.example.money_machine.data.transaction.Transaction

// this list all possible UI actions the user can take on the Transaction screen
sealed class AddTransactionUIAction {
  data class InsertTransaction(
    val transaction: Transaction
  ): AddTransactionUIAction()
}