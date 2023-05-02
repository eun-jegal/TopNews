package com.example.topnews.data.repository

import com.example.topnews.data.model.Article
import com.example.topnews.data.model.News
import com.example.topnews.data.other.Resource

interface NewsRepository {
    suspend fun getTopHeadlines(country: String, page: Int): Resource<News>

    suspend fun getHeadlinesByCategory(
        country: String,
        page: Int,
        category: String
    ): Resource<News>

    suspend fun getSearchedTopHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<News>

    suspend fun saveArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun getAllArticles(): List<Article>

    fun deleteAllArticles()
}