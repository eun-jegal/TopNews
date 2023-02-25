package com.example.topnews.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.topnews.domain.*

class NewsViewModel(
    private val app: Application,
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getHeadlinesByCategoryUseCase: GetHeadlinesByCategoryUseCase,
    private val getSearchedHeadlinesUseCase: GetSearchedHeadlinesUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val getAllArticlesUseCase: GetAllArticlesUseCase,
    private val deleteAllArticlesUseCase: DeleteAllArticlesUseCase
) : AndroidViewModel(app) {
}