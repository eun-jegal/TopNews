package com.example.topnews.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.topnews.data.model.Article
import com.example.topnews.data.model.News
import com.example.topnews.data.repository.NewsRepository
import com.example.topnews.data.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel constructor(
    private val newsRepository: NewsRepository
    ) : ViewModel() {

    val topHeadlines: MutableLiveData<NetworkResult<News>> = MutableLiveData()
    val headlinesByCategory: MutableLiveData<NetworkResult<News>> = MutableLiveData()
    val searchedHeadlines: MutableLiveData<NetworkResult<News>> = MutableLiveData()
    val categoryList = listOf("general", "business", "technology", "entertainment", "health", "sports", "science")

    fun getTopHeadlines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        topHeadlines.postValue(NetworkResult.Loading())
        try {
            val apiResult = newsRepository.getTopHeadlines(country, page)
            topHeadlines.postValue(apiResult)
        } catch (e: Exception) {
            topHeadlines.postValue(NetworkResult.Error(e.message.toString()))
        }
    }

    fun getHeadlinesByCategory(country: String, page: Int, category: String) =
        viewModelScope.launch(Dispatchers.IO) {
            headlinesByCategory.postValue(NetworkResult.Loading())
            try {
                val apiResult = newsRepository.getHeadlinesByCategory(country, page, category)
                headlinesByCategory.postValue(apiResult)
            } catch (e: Exception) {
                headlinesByCategory.postValue(NetworkResult.Error(e.message.toString()))
            }
        }

    fun getSearchedHeadlines(country: String, searchQuery: String, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            searchedHeadlines.postValue(NetworkResult.Loading())
            try {
                val apiResult = newsRepository.getSearchedTopHeadlines(country, searchQuery, page)
                searchedHeadlines.postValue(apiResult)
            } catch (e: Exception) {
                searchedHeadlines.postValue(NetworkResult.Error(e.message.toString()))
            }
        }

    fun saveArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.saveArticle(article)
        }
    }

    fun getSavedArticles() = liveData {
        val articlesFromDB = newsRepository.getAllArticles()
        emit(articlesFromDB)
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.deleteArticle(article)
        }
    }

    fun deleteAllArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.deleteAllArticles()
        }
    }
}