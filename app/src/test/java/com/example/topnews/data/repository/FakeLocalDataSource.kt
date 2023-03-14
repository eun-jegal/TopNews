package com.example.topnews.data.repository

import com.example.topnews.data.NewsLocalDataSource
import com.example.topnews.data.model.Article
import com.example.topnews.domain.TestUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource: NewsLocalDataSource {
    override suspend fun saveArticle(article: Article) {
        // Nothing
    }

    override suspend fun deleteArticle(article: Article) {
        // Nothing
    }

    override fun getAllArticles(): Flow<List<Article>> {
        return flow {
            emit(TestUtil.testArticleList)
        }
    }

    override fun deleteAllArticles() {
        // Nothing
    }
}