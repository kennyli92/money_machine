package com.example.money_machine.dagger

import com.example.money_machine.transaction.fragment.AddTransactionFragment
import com.example.money_machine.transaction.fragment.TransactionsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
  fun inject(fragment: TransactionsFragment)
  fun inject(fragment: AddTransactionFragment)
}