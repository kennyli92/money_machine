package com.example.money_machine

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.nav_spending -> {
//                message.setText(R.string.tab_spending)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.nav_saving -> {
//                message.setText(R.string.tab_saving)
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
//        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun setupNavigation() {
        val navController = Navigation.findNavController(this, R.id.nav_fragment)
        setupActionBarWithNavController(navController)
        NavigationUI.setupWithNavController(bottom_navigation, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_fragment).navigateUp()
    }
}
