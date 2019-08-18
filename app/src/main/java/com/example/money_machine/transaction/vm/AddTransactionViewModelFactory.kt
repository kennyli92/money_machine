package com.example.money_machine.transaction.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.money_machine.data.transaction.TransactionRepository

class AddTransactionViewModelFactory(
  private val transactionRepository: TransactionRepository
) : ViewModelProvider.Factory {

  @SuppressWarnings("unchecked")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return AddTransactionViewModel(
      transactionRepository = transactionRepository) as T
  }
}