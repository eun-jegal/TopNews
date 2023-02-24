package com.example.topnews.data.repository

import com.example.topnews.data.model.Article
import com.example.topnews.data.model.News
import com.example.topnews.data.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines(country: String, page: Int): NetworkResult<News>

    suspend fun getHeadlinesByCategory(
        country: String,
        page: Int,
        category: String
    ): NetworkResult<News>

    suspend fun getSearchedTopHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): NetworkResult<News>

    suspend fun saveArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun getAllArticles(): Flow<List<Article>>

    fun deleteAllArticles()
}