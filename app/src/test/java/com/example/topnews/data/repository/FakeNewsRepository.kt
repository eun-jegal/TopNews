package com.example.topnews.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.topnews.data.model.Article
import com.example.topnews.data.model.News
import com.example.topnews.data.other.Resource

class FakeNewsRepository: NewsRepository {

    private val articleList = mutableListOf<Article>()
    private val observableArticleList = MutableLiveData<List<Article>>(articleList)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshArticleList() {
        observableArticleList.postValue(articleList)
    }

    override suspend fun getTopHeadlines(country: String, page: Int): Resource<News> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Something is wrong", null)
        } else {
            Resource.Success(News(articles = articleList, status = "ok", totalResults = 0))
        }
    }

    override suspend fun getHeadlinesByCategory(
        country: String,
        page: Int,
        category: String
    ): Resource<News> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Something is wrong", null)
        } else {
            Resource.Success(News(articles = articleList, status = "ok", totalResults = 0))
        }
    }

    override suspend fun getSearchedTopHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<News> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Something is wrong", null)
        } else {
            Resource.Success(News(articles = articleList, status = "ok", totalResults = 0))
        }
    }

    override suspend fun saveArticle(article: Article) {
        articleList.add(article)
        refreshArticleList()
    }

    override suspend fun deleteArticle(article: Article) {
        articleList.remove(article)
        refreshArticleList()
    }

    override fun getAllArticles(): List<Article> {
        return observableArticleList.value!!
    }

    override fun deleteAllArticles() {
        articleList.clear()
        refreshArticleList()
    }
}