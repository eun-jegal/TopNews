package com.example.topnews.domain

import com.example.topnews.data.repository.NewsRepository

class DeleteAllArticlesUseCase(
    private val newsRepository: NewsRepository
) {
    fun deleteAllArticles() {
        newsRepository.deleteAllArticles()
    }
}