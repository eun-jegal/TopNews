package com.example.topnews.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.topnews.domain.*
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getHeadlinesByCategoryUseCase: GetHeadlinesByCategoryUseCase,
    private val getSearchedHeadlinesUseCase: GetSearchedHeadlinesUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val getAllArticlesUseCase: GetAllArticlesUseCase,
    private val deleteAllArticlesUseCase: DeleteAllArticlesUseCase
) : ViewModel() {
}