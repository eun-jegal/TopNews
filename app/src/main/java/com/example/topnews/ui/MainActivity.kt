package com.example.topnews.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.topnews.R
import com.example.topnews.databinding.ActivityMainBinding
import com.example.topnews.ui.adapter.NewsAdapter
import com.example.topnews.ui.viewmodel.NewsViewModel
import com.example.topnews.ui.viewmodel.NewsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var binding: ActivityMainBinding
    lateinit var newsAdapter: NewsAdapter
    lateinit var viewModel: NewsViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_feed, R.id.navigation_browse, R.id.navigation_saved
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
        newsAdapter = NewsAdapter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}