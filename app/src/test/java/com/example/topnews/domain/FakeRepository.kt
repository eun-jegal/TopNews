package com.example.topnews.domain

import com.example.topnews.data.model.Article
import com.example.topnews.data.model.News
import com.example.topnews.data.repository.NewsRepository
import com.example.topnews.data.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

class FakeRepository: NewsRepository {
    override suspend fun getTopHeadlines(country: String, page: Int): NetworkResult<News> {
        return when (country) {
            "us" -> NetworkResult.Success(News(articles = listOf(), status = "ok", totalResults = 20))
            else -> NetworkResult.Error("Unexpected Error")
        }
    }

    override suspend fun getHeadlinesByCategory(
        country: String,
        page: Int,
        category: String
    ): NetworkResult<News> {
        return when (category) {
            "General" -> NetworkResult.Success(News(articles = listOf(), status = "ok", totalResults = 20))
            else -> NetworkResult.Error("Unexpected Error")
        }
    }

    override suspend fun getSearchedTopHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): NetworkResult<News> {
        return when (searchQuery) {
            "sport" -> NetworkResult.Success(News(articles = listOf(), status = "ok", totalResults = 20))
            else -> NetworkResult.Error("Unexpected Error")
        }
    }

    override suspend fun saveArticle(article: Article) {
        // Nothing
    }

    override suspend fun deleteArticle(article: Article) {
        // Nothing
    }

    override fun getAllArticles(): Flow<List<Article>> {
        return  flow {
            emit(TestUtil.testArticleList)
        }
    }

    override fun deleteAllArticles() {
        // Nothing
    }
}