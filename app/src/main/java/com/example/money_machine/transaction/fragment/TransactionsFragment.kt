package com.example.money_machine.transaction.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_machine.MainActivity
import com.example.money_machine.R
import com.example.money_machine.dagger.ActivityComponent
import com.example.money_machine.dagger.Injector
import com.example.money_machine.data.transaction.Transaction
import com.example.money_machine.data.transaction.TransactionRepository
import com.example.money_machine.data.transaction.TransactionTag
import com.example.money_machine.transaction.vm.TransactionUIAction
import com.example.money_machine.transaction.vm.TransactionViewModel
import com.example.money_machine.transaction.vm.TransactionViewModelFactory
import com.example.money_machine.transaction.vm.TransactionsAdapter
import com.example.money_machine.util.DisposableOnLifecycleChange
import com.example.money_machine.util.Logger
import com.example.money_machine.util.ResetDependencyOnDestroy
import com.smshift.smshift.extensions.plusAssign
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import java.util.*
import javax.inject.Inject

class TransactionsFragment : Fragment() {
  private var component: ActivityComponent by ResetDependencyOnDestroy()
  private val disposables: CompositeDisposable by DisposableOnLifecycleChange()
  @set:Inject
  var transactionRepository: TransactionRepository by ResetDependencyOnDestroy()
  private lateinit var vm: TransactionViewModel
  private lateinit var adapter: TransactionsAdapter
  private var transactionType: Int = 0

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    component = Injector.getActivityComponent(activity = activity as MainActivity)
    component.inject(this)

    arguments?.let {
      val passedArg = TransactionsFragmentArgs.fromBundle(it)
      transactionType = passedArg.transactionType
    }

    val view =  inflater.inflate(R.layout.fragment_transactions, container, false)
    vm = ViewModelProviders.of(
      this,
      TransactionViewModelFactory(
        transactionType = transactionType,
        transactionRepository = transactionRepository)
    ).get(TransactionViewModel::class.java)

    setHasOptionsMenu(true)
    val recyclerView = view.transaction_recycler_view
    adapter = TransactionsAdapter()
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.adapter = adapter
    return view
  }

  override fun onResume() {
    // this consumes user UI actions to be processed in the VM
    vm.uiActionHandler(actionObs = Observable.just(TransactionUIAction.Load))

    // this listens to a property observable for UI actions. In this case, updating the recycler view
    disposables += vm.transactions()
      .subscribe({ transactions ->
        adapter.items = transactions
      }, Logger.logErrorAndThrow())

    super.onResume()
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.transaction, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.transaction_add -> {
        val action =
          TransactionsFragmentDirections.actionAddTransaction(
            transactionType = transactionType
          )
        view!!.findNavController().navigate(action)
        return true
      }
    }
    return false
  }
}