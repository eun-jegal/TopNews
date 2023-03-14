package com.example.topnews.data

import com.example.topnews.data.model.Article

interface NewsLocalDataSource {
    suspend fun saveArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun getAllArticles(): List<Article>

    fun deleteAllArticles()
}