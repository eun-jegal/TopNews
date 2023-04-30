package com.example.topnews.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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
        initNavigationController()

        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
        newsAdapter = NewsAdapter()
    }

    private fun initNavigationController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavView: BottomNavigationView = binding.navView
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> bottomNavView.visibility = View.GONE
                else -> bottomNavView.visibility = View.VISIBLE
            }
        }

        bottomNavView.setupWithNavController(navController)
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