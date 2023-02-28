package com.example.topnews.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.topnews.R
import com.example.topnews.data.model.Article
import com.example.topnews.data.util.NetworkResult
import com.example.topnews.databinding.FragmentFeedBinding
import com.example.topnews.ui.adapter.NewsAdapter
import com.example.topnews.ui.viewmodel.NewsViewModel

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private val country = "us"
    private val page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.apply {
            requestFocus()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeedBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(
                R.id.action_navigation_feed_to_detailFragment,
                bundle
            )
        }
        initRecyclerView()
        displayContent()
    }

    private fun initRecyclerView() {
        binding.recyclerviewHeadlines.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun displayContent() {
        viewModel.getTopHeadlines(country, page)
        viewModel.topHeadlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        val articleList = it.articles
                        Log.d("Eun", "size: " + articleList.size)
                        displayMainThumbnail(articleList[0])
                        displayRecyclerView(articleList.subList(1, articleList.size - 1))
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

    private fun displayMainThumbnail(article: Article) {
        Glide.with(binding.mainHeadlineThumbnail.context)
            .load(article.urlToImage)
            .into(binding.mainHeadlineThumbnail)
        binding.mainHeadlineTitle.text = article.title
    }

    private fun displayRecyclerView(list: List<Article>) {
        newsAdapter.differ.submitList(list)
    }
}