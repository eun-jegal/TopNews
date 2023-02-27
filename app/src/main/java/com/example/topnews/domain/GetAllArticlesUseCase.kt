package com.example.topnews.domain

import com.example.topnews.data.model.Article
import com.example.topnews.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetAllArticlesUseCase(
    private val newsRepository: NewsRepository
) {
    fun execute(): Flow<List<Article>> {
        return newsRepository.getAllArticles()
    }
}