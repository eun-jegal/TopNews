package com.example.topnews.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.topnews.domain.*

class NewsViewModelFactory(
    private val app: Application,
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getHeadlinesByCategoryUseCase: GetHeadlinesByCategoryUseCase,
    private val getSearchedHeadlinesUseCase: GetSearchedHeadlinesUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val getAllArticlesUseCase: GetAllArticlesUseCase,
    private val deleteAllArticlesUseCase: GetAllArticlesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            app,
            getTopHeadlinesUseCase,
            getHeadlinesByCategoryUseCase,
            getSearchedHeadlinesUseCase,
            saveArticleUseCase,
            deleteArticleUseCase,
            getAllArticlesUseCase,
            deleteAllArticlesUseCase
        ) as T
    }
}