package com.example.topnews.data.repository

import com.example.topnews.data.NewsLocalDataSource
import com.example.topnews.data.model.Article
import com.example.topnews.domain.TestUtil

class FakeLocalDataSource: NewsLocalDataSource {
    override suspend fun saveArticle(article: Article) {
        // Nothing
    }

    override suspend fun deleteArticle(article: Article) {
        // Nothing
    }

    override fun getAllArticles(): List<Article> {
        return TestUtil.testArticleList
    }

    override fun deleteAllArticles() {
        // Nothing
    }
}