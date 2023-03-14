package com.example.topnews.data

import com.example.topnews.data.db.ArticleDAO
import com.example.topnews.data.model.Article

class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
) : NewsLocalDataSource {
    override suspend fun saveArticle(article: Article) {
        articleDAO.save(article)
    }

    override suspend fun deleteArticle(article: Article) {
        articleDAO.delete(article)
    }

    override fun getAllArticles(): List<Article> {
        return articleDAO.getAllArticles()
    }

    override fun deleteAllArticles() {
        articleDAO.deleteAllArticles()
    }
}