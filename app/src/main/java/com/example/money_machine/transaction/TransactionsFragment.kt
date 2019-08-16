package com.example.money_machine.transaction

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.money_machine.R
import com.example.money_machine.db.transaction.Transaction
import com.example.money_machine.db.transaction.TransactionTag
import com.smshift.smshift.extensions.plusAssign
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import java.util.*

class TransactionsFragment : Fragment() {
  // TODO: dispose on destroy
  private val disposables: CompositeDisposable = CompositeDisposable()
  // TODO: reset on destroy
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

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    adapter.items = listOf(Transaction(
      userId = "Foo Bar", date = Date(), amount = "$123.00", tag = TransactionTag.OTHER, description = "description"))

    arguments?.let {
      val passedArg = TransactionsFragmentArgs.fromBundle(it)
      transactionType = passedArg.transactionType
    }

    super.onViewCreated(view, savedInstanceState)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.transaction, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.transaction_add -> {
        val action = TransactionsFragmentDirections.actionAddTransaction(transactionType = transactionType)
        view!!.findNavController().navigate(action)
        return true
      }
    }
    return false
  }
}