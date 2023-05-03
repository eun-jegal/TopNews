package com.example.topnews.ui.viewmodel

import androidx.lifecycle.*
import com.example.topnews.data.model.Article
import com.example.topnews.data.model.News
import com.example.topnews.data.other.Resource
import com.example.topnews.data.repository.NewsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _topHeadlines = MutableLiveData<Resource<News>>()
    val topHeadlines: LiveData<Resource<News>> = _topHeadlines

    private val _headlinesByCategory = MutableLiveData<Resource<News>>()
    val headlinesByCategory: LiveData<Resource<News>> = _headlinesByCategory

    private val _searchedHeadlines = MutableLiveData<Resource<News>>()
    val searchedHeadlines: LiveData<Resource<News>> = _searchedHeadlines

    fun getTopHeadlines(country: String, page: Int) {
        _topHeadlines.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = newsRepository.getTopHeadlines(country, page)
            _topHeadlines.postValue(response)
        }
    }

    fun getHeadlinesByCategory(country: String, page: Int, category: String) {
        _headlinesByCategory.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = newsRepository.getHeadlinesByCategory(country, page, category)
            _headlinesByCategory.postValue(response)
        }
    }

    fun getSearchedHeadlines(country: String, searchQuery: String, page: Int) {
        _searchedHeadlines.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = newsRepository.getSearchedTopHeadlines(country, searchQuery, page)
            _searchedHeadlines.postValue(response)
        }
    }


    fun saveArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.saveArticle(article)
        }
    }

    fun getSavedArticles() = liveData {
        emit(newsRepository.getAllArticles())
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.deleteArticle(article)
        }
    }

    fun deleteAllArticles() {
        viewModelScope.launch {
            newsRepository.deleteAllArticles()
        }
    }
}