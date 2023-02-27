package com.example.topnews.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.topnews.R
import com.example.topnews.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

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