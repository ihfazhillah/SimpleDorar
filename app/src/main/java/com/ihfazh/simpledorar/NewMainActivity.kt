package com.ihfazh.simpledorar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ihfazh.simpledorar.databinding.ActivityNewMainBinding

class NewMainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewMainBinding.inflate(layoutInflater).apply {
            val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
            navController = navHost.navController

            bottomNavigation.setupWithNavController(navController)
        }
        setContentView(binding.root)
    }
}