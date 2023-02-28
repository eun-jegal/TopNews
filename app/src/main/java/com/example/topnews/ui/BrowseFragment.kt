package com.example.topnews.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topnews.R
import com.example.topnews.data.model.Article
import com.example.topnews.data.util.NetworkResult
import com.example.topnews.databinding.FragmentBrowseBinding
import com.example.topnews.ui.adapter.NewsAdapter
import com.example.topnews.ui.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayout

class BrowseFragment : Fragment() {

    private lateinit var binding: FragmentBrowseBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private val country = "us"
    private val page = 1
    private val categorySet = listOf("general", "business", "technology", "entertainment", "health", "sports", "science")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_browse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBrowseBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        initRecyclerView()
        initTabLayout()
//        displayContentByCategory("General")
    }

    private fun initRecyclerView() {
        binding.recyclerviewHeadlines.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initTabLayout() {
        binding.categoryTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabPosition = tab?.position
                displayContentByCategory(categorySet[tabPosition!!])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun displayContentByCategory(category: String) {
        viewModel.getHeadlinesByCategory(country, page, category)
        viewModel.headlinesByCategory.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        val articleList = it.articles
                        displayRecyclerView(articleList)
                    }
                }
                is NetworkResult.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
                is NetworkResult.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun displayRecyclerView(list: List<Article>) {
        newsAdapter.differ.submitList(list)
    }
}