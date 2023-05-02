package com.example.topnews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.topnews.R
import com.example.topnews.data.model.Article
import com.example.topnews.data.other.Resource
import com.example.topnews.databinding.FragmentFeedBinding
import com.example.topnews.ui.adapter.NewsAdapter
import com.example.topnews.ui.viewmodel.NewsViewModel

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private val country = "us"
    private val page = 1

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
            isNestedScrollingEnabled = false
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun displayContent() {
        viewModel.getTopHeadlines(country, page)
        viewModel.topHeadlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let {
                        val articleList = it.articles
                        displayMainThumbnail(articleList[0])
                        displayRecyclerView(articleList.subList(1, articleList.size - 1))
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        showErrorMessage(it)
                    }
                }
                is Resource.Loading -> {
                    hideErrorMessage()
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

    private fun showErrorMessage(message: String) {
        binding.textErrorMessage.apply {
            visibility = View.VISIBLE
            text = message
        }
    }

    private fun hideErrorMessage() {
        binding.textErrorMessage.visibility = View.GONE
    }

    private fun displayMainThumbnail(article: Article) {
        Glide.with(binding.mainHeadlineThumbnail.context)
            .load(article.urlToImage)
            .fitCenter()
            .into(binding.mainHeadlineThumbnail)
        binding.mainHeadlineTitle.text = article.title
    }

    private fun displayRecyclerView(list: List<Article>) {
        newsAdapter.differ.submitList(list)
    }
}