package com.ihfazh.simpledorarnew

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        val appbarConfiguration = AppBarConfiguration(navController.graph)

        val myToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(myToolbar)
        myToolbar.setupWithNavController(
            navController = navController, appbarConfiguration
        )

        val bottomToolbar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomToolbar.setupWithNavController(navController)
    }

    companion object {
        const val SKEY = "com.ihfazh.simpledorarnew.skey"
        private val TAG = MainActivity::class.java.simpleName
    }
}