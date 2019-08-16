package com.example.money_machine.data.transaction

sealed class GetTransactionsResponse {
  data class Success(val transactions: List<Transaction>) : GetTransactionsResponse()
  object NoTransactions : GetTransactionsResponse()
  data class Error(val message: String) : GetTransactionsResponse()
}