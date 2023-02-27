package com.example.topnews.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.topnews.R
import com.example.topnews.data.model.News
import com.example.topnews.databinding.FragmentDetailBinding
import com.example.topnews.ui.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        binding.toolbar.inflateMenu(R.menu.sub_toolbar_menu)
        viewModel = (activity as MainActivity).viewModel

        initActionbar()
        initWebView()
    }

    private fun initActionbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_share -> {
                    Log.d("Eun", "action_share")
                    true
                }
                R.id.action_save -> {
                    Log.d("Eun", "action_save")
                    true
                }
                else -> false
            }
        }
    }

    private fun initWebView() {
        val args: DetailFragmentArgs by navArgs()
        val article = args.selectedArticle
        binding.wvInfo.apply {
            webViewClient = WebViewClient()
            article.url?.let {
                loadUrl(it)
            }
        }
    }
}