package com.example.money_machine.transaction.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.money_machine.R
import com.example.money_machine.dagger.ActivityComponent
import com.example.money_machine.util.DisposableOnLifecycleChange
import com.example.money_machine.util.ResetDependencyOnDestroy
import io.reactivex.disposables.CompositeDisposable

class AddTransactionFragment : Fragment() {
  private var component: ActivityComponent by ResetDependencyOnDestroy()
  private val disposables: CompositeDisposable by DisposableOnLifecycleChange()
  private var transactionType: Int = 0

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_add_transaction, container, false)
    setHasOptionsMenu(true)

    return view
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    arguments?.let {
      val passedArg = TransactionsFragmentArgs.fromBundle(it)
      transactionType = passedArg.transactionType
    }

    (activity as AppCompatActivity).supportActionBar!!.title =
      resources.getString(R.string.transaction_add_arg, resources.getString(transactionType))
    super.onActivityCreated(savedInstanceState)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.add_transaction, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.transaction_done -> {
        // TODO insert to db
        return true
      }
    }
    return false
  }
}