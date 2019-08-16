package com.example.money_machine

import android.os.Bundle
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
    bottom_navigation.setupWithNavController(navController)
  }

  override fun onSupportNavigateUp(): Boolean {
    return findNavController(R.id.nav_fragment).navigateUp()
  }
}
