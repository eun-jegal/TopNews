package com.example.topnews.domain

import com.example.topnews.data.model.News
import com.example.topnews.data.repository.NewsRepository
import com.example.topnews.data.util.NetworkResult

class GetHeadlinesByCategoryUseCase(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(country: String, page: Int, category: String): NetworkResult<News> {
        return newsRepository.getHeadlinesByCategory(country, page, category)
    }
}