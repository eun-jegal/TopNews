package com.example.topnews.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topnews.data.model.News
import com.example.topnews.data.util.NetworkResult
import com.example.topnews.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getHeadlinesByCategoryUseCase: GetHeadlinesByCategoryUseCase,
    private val getSearchedHeadlinesUseCase: GetSearchedHeadlinesUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val getAllArticlesUseCase: GetAllArticlesUseCase,
    private val deleteAllArticlesUseCase: DeleteAllArticlesUseCase
) : ViewModel() {

    val topHeadlines: MutableLiveData<NetworkResult<News>> = MutableLiveData()
    val headlinesByCategory: MutableLiveData<NetworkResult<News>> = MutableLiveData()
    val searchedHeadlines: MutableLiveData<NetworkResult<News>> = MutableLiveData()

    fun getTopHeadlines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        topHeadlines.postValue(NetworkResult.Loading())
        try {
            val apiResult = getTopHeadlinesUseCase.execute(country, page)
            topHeadlines.postValue(apiResult)
        } catch (e: Exception) {
            topHeadlines.postValue(NetworkResult.Error(e.message.toString()))
        }
    }

    fun getHeadlinesByCategory(country: String, page: Int, category: String) = viewModelScope.launch(Dispatchers.IO) {
        headlinesByCategory.postValue(NetworkResult.Loading())
        try {
            val apiResult = getHeadlinesByCategoryUseCase.execute(country, page, category)
            headlinesByCategory.postValue(apiResult)
        } catch (e: Exception) {
            headlinesByCategory.postValue(NetworkResult.Error(e.message.toString()))
        }
    }
}