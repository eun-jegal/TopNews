package com.example.topnews.domain

import com.example.topnews.data.model.Article
import com.example.topnews.data.repository.NewsRepository

class GetAllArticlesUseCase(
    private val newsRepository: NewsRepository
) {
    fun execute(): List<Article> {
        return newsRepository.getAllArticles()
    }
}