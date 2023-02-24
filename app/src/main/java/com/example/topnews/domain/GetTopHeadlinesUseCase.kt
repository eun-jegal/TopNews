package com.example.topnews.domain

import com.example.topnews.data.model.News
import com.example.topnews.data.repository.NewsRepository
import com.example.topnews.data.util.NetworkResult

class GetTopHeadlinesUseCase(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(country: String, page: Int): NetworkResult<News> {
        return newsRepository.getTopHeadlines(country, page)
    }
}