package com.example.topnews.domain

import com.example.topnews.data.model.Article
import com.example.topnews.data.repository.NewsRepository

class SaveArticleUseCase(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(article: Article) {
        newsRepository.saveArticle(article)
    }
}