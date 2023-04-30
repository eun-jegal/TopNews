package com.example.topnews.data.repository

import com.example.topnews.data.NewsLocalDataSource
import com.example.topnews.data.model.Article

class FakeNewsLocalDataSource: NewsLocalDataSource {

    private val articles = mutableListOf<Article>()

    override suspend fun saveArticle(article: Article) {
        articles.add(article)
    }

    override suspend fun deleteArticle(article: Article) {
        articles.remove(article)
    }

    override fun getAllArticles(): List<Article> {
        return articles
    }

    override fun deleteAllArticles() {
        articles.clear()
    }
}