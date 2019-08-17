package com.example.money_machine.transaction.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_machine.R
import com.example.money_machine.dagger.ActivityComponent
import com.example.money_machine.data.transaction.Transaction
import com.example.money_machine.data.transaction.TransactionTag
import com.example.money_machine.transaction.vm.TransactionsAdapter
import com.example.money_machine.util.DisposableOnLifecycleChange
import com.example.money_machine.util.ResetDependencyOnDestroy
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import java.util.*

class TransactionsFragment : Fragment() {
  private var component: ActivityComponent by ResetDependencyOnDestroy()
  private val disposables: CompositeDisposable by DisposableOnLifecycleChange()
  private lateinit var adapter: TransactionsAdapter
  private var transactionType: Int = 0

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view =  inflater.inflate(R.layout.fragment_transactions, container, false)
    setHasOptionsMenu(true)
    val recyclerView = view.transaction_recycler_view
    adapter = TransactionsAdapter()
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.adapter = adapter
    return view
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    adapter.items = listOf(Transaction(
      userId = "Foo Bar", date = Date(), amount = "$123.00", tag = TransactionTag.OTHER, description = "description"))

    arguments?.let {
      val passedArg = TransactionsFragmentArgs.fromBundle(it)
      transactionType = passedArg.transactionType
    }

    super.onActivityCreated(savedInstanceState)
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