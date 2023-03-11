package com.example.topnews.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.topnews.R
import com.example.topnews.databinding.FragmentDetailBinding
import com.example.topnews.ui.viewmodel.NewsViewModel

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
        viewModel = (activity as MainActivity).viewModel

        initActionbar()
        initWebView()
    }

    private fun initActionbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            val args: DetailFragmentArgs by navArgs()
            val article = args.selectedArticle
            article.title?.let {
                val dashIndex = it.lastIndexOf('-')
                val company = it.substring(dashIndex + 2, it.length)
                binding.toolbarSource.text = company
                binding.toolbarUrl.text = article.url
            }

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_share -> {
                        val share = Intent.createChooser(Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, article.url)
                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        }, null)
                        startActivity(share)
                        true
                    }
                    R.id.action_save -> {
                        viewModel.saveArticle(article)
                        Toast.makeText(activity, "Saved the article!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
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