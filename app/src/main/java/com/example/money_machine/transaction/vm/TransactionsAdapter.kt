package com.example.money_machine.transaction.vm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.money_machine.R
import com.example.money_machine.data.transaction.Transaction
import com.smshift.smshift.extensions.checkNotNull

class TransactionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private var _items: List<Transaction> = emptyList()
  var items: List<Transaction>
    get() = _items
    set(value) {
      _items = value
      this.notifyDataSetChanged()
    }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun getItemViewType(position: Int): Int {
    return R.layout.item_transaction
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    (holder as TransactionViewHolder).bind(item = items[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val inflater = LayoutInflater.from(parent.context.checkNotNull())
    return TransactionViewHolder(
      view = inflater.inflate(
        viewType,
        parent,
        false
      )
    )
  }
}