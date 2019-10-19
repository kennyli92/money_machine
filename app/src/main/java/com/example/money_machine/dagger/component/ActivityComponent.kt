package com.example.money_machine.dagger.component

import com.example.money_machine.dagger.module.ActivityModule
import com.example.money_machine.dagger.scope.ActivityScope
import com.example.money_machine.transaction.fragment.AddTransactionFragment
import com.example.money_machine.transaction.fragment.TransactionsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
  fun inject(fragment: TransactionsFragment)
  fun inject(fragment: AddTransactionFragment)
}