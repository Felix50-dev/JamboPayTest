package com.example.jambopaytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import com.example.jambopaytest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val bottomNavigationView = binding.bottomNavigation

        // Handle navigation item selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_users -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, UserFragment())
                        .commit()
                    true
                }
                R.id.nav_results -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ChartFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

        // Load the default fragment when the activity starts
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.nav_users
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}