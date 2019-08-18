package com.example.money_machine

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupNavigation()
  }

  private fun setupNavigation() {
    val navController = findNavController(R.id.nav_fragment)
    setSupportActionBar(toolbar)
    setupActionBarWithNavController(navController)
    // let nav component manage bottom nav bar
    bottom_navigation.setupWithNavController(navController)
    // hide bottom nav bar in nested level screens
    navController.addOnDestinationChangedListener { _, destination, _ ->
      when(destination.id) {
        R.id.fragment_add_transaction -> hideBottomNav()
        else -> showBottomNav()
      }
      hideKeyboard()
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    return findNavController(R.id.nav_fragment).navigateUp()
  }

  private fun showBottomNav() {
    bottom_navigation.visibility = View.VISIBLE

  }

  private fun hideBottomNav() {
    bottom_navigation.visibility = View.GONE

  }

  private fun hideKeyboard() {
    val imm = this.getSystemService(InputMethodManager::class.java)
    var view = this.currentFocus
    if (view == null) {
      view = View(this)
    }

    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }
}
