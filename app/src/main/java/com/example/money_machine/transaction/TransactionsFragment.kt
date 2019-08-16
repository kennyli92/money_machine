package com.example.money_machine.transaction

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.money_machine.R
import kotlinx.android.synthetic.main.activity_main.view.*

class TransactionsFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view =  inflater.inflate(R.layout.fragment_transactions, container, false)
    setHasOptionsMenu(true)
    return view
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.transaction, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }
}