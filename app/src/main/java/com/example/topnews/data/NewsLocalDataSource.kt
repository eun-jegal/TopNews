package com.example.topnews.data

import com.example.topnews.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun getAllArticles(): Flow<List<Article>>

    fun deleteAllArticles()
}