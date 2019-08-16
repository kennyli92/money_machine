package com.example.money_machine.transaction

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.money_machine.R

class AddTransactionFragment : Fragment() {
  // TODO: reset on destroy
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return  inflater.inflate(R.layout.fragment_add_transaction, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {

    super.onActivityCreated(savedInstanceState)
  }
}