package com.example.topnews.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.topnews.domain.*
import javax.inject.Inject

class NewsViewModelFactory @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getHeadlinesByCategoryUseCase: GetHeadlinesByCategoryUseCase,
    private val getSearchedHeadlinesUseCase: GetSearchedHeadlinesUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val getAllArticlesUseCase: GetAllArticlesUseCase,
    private val deleteAllArticlesUseCase: DeleteAllArticlesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
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