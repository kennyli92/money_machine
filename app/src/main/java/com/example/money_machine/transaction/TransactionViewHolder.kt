package com.example.money_machine.transaction

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.money_machine.db.transaction.Transaction
import kotlinx.android.synthetic.main.item_transaction.view.*

class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  fun bind(item: Transaction) {
    itemView.item_transaction_id.text = item.userId
    itemView.item_transaction_date.text = item.date.toString()
    itemView.item_transaction_amount.text = item.amount
    itemView.item_transaction_tag.text = item.tag.label
    itemView.item_transaction_description.text = item.description
  }
}