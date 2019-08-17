package com.example.money_machine.transaction.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.money_machine.data.transaction.TransactionRepository

class TransactionViewModelFactory(
  private val transactionType: Int,
  private val transactionRepository: TransactionRepository
) : ViewModelProvider.Factory {

  @SuppressWarnings("unchecked")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return TransactionViewModel(
      transactionType = transactionType,
      transactionRepository = transactionRepository) as T
  }
}