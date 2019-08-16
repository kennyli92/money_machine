package com.example.money_machine.data.transaction

sealed class InsertTransactionResponse {
  object Success : InsertTransactionResponse()
  data class Error(val message: String) : InsertTransactionResponse()
}