package com.example.topnews.domain

import com.example.topnews.data.model.News
import com.example.topnews.data.repository.NewsRepository
import com.example.topnews.data.util.NetworkResult

class GetSearchedHeadlinesUseCase(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(
        country: String,
        searchQuery: String,
        page: Int
    ): NetworkResult<News> {
        return newsRepository.getSearchedTopHeadlines(country, searchQuery, page)
    }
}