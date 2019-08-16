package com.example.money_machine.transaction

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.money_machine.R

class AddTransactionFragment : Fragment() {
  private var transactionType: Int = 0
  // TODO: reset on destroy
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return  inflater.inflate(R.layout.fragment_add_transaction, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    arguments?.let {
      val passedArg = TransactionsFragmentArgs.fromBundle(it)
      transactionType = passedArg.transactionType
    }

    (activity as AppCompatActivity).supportActionBar!!.title =
      resources.getString(R.string.transaction_add_arg, resources.getString(transactionType))
    super.onViewCreated(view, savedInstanceState)
  }
}