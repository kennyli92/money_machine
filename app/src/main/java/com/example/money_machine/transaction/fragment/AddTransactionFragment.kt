package com.example.money_machine.transaction.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.money_machine.App
import com.example.money_machine.MainActivity
import com.example.money_machine.R
import com.example.money_machine.dagger.component.ActivityComponent
import com.example.money_machine.data.transaction.Transaction
import com.example.money_machine.data.transaction.TransactionRepository
import com.example.money_machine.data.transaction.TransactionTag
import com.example.money_machine.extensions.application
import com.example.money_machine.extensions.plusAssign
import com.example.money_machine.extensions.requireNotNull
import com.example.money_machine.transaction.vm.*
import com.example.money_machine.util.DisposableOnLifecycleChange
import com.example.money_machine.util.Logger
import com.example.money_machine.util.ResetDependencyOnDestroy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_add_transaction.view.*
import java.util.*
import javax.inject.Inject

class AddTransactionFragment : Fragment() {
  private var component: ActivityComponent by ResetDependencyOnDestroy()
  private val disposables: CompositeDisposable by DisposableOnLifecycleChange()
  @set:Inject
  var transactionRepository: TransactionRepository by ResetDependencyOnDestroy()
  private var transactionType: Int = 0
  private var isSpending: Boolean = false
  private lateinit var vm: AddTransactionViewModel
  private val addTransactionSignal = PublishSubject.create<Any>()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val app = application().requireNotNull() as App
    component = app.activityComponent(activity = activity.requireNotNull() as MainActivity)
    component.inject(this)

    arguments?.let {
      val passedArg = TransactionsFragmentArgs.fromBundle(it)
      transactionType = passedArg.transactionType
      isSpending = when(transactionType) {
        R.string.transaction_spending -> true
        else -> false
      }
    }

    val view = inflater.inflate(R.layout.fragment_add_transaction, container, false)
    setHasOptionsMenu(true)
    vm = ViewModelProviders.of(
      this,
      AddTransactionViewModelFactory(
        transactionRepository = transactionRepository)
    ).get(AddTransactionViewModel::class.java)

    return view
  }

  override fun onResume() {
    // set dynamic title
    (activity as AppCompatActivity).supportActionBar!!.title =
      resources.getString(R.string.transaction_add_arg, resources.getString(transactionType))

    // upon observing add transaction signal, assemble addTransactionUIAction to be passed to the VM
    val addTransactionUIActionSignal = addTransactionSignal.map {
      val datePicker = view!!.add_transaction_date
      val calendar = Calendar.getInstance()
      calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)

      val transaction = Transaction(
        userId = view!!.add_transaction_user_id.text.toString(),
        date = calendar.time,
        description = view!!.add_transaction_description.text.toString(),
        amount = view!!.add_transaction_amount.text.toString(),
        tag = TransactionTag.valueOf(view!!.add_transaction_tag.selectedItem.toString()),
        isSpending = isSpending
      )

      return@map AddTransactionUIAction.InsertTransaction(transaction = transaction) as AddTransactionUIAction
    }
    // this consumes user UI actions to be processed in the VM
    disposables += vm.uiActionHandler(actionObs = addTransactionUIActionSignal)

    // pop screen off back stack upon success transaction insertion
    disposables += vm.isTransactionInsertedObs()
      .subscribe({
        view!!.findNavController().popBackStack()
      }, Logger.logErrorAndThrow())
    super.onResume()
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.add_transaction, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.transaction_done -> {
        addTransactionSignal.onNext("Send add transaction signal")
        return true
      }
    }
    return false
  }
}