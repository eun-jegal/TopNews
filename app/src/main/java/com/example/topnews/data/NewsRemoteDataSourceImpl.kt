package com.example.topnews.data

import com.example.topnews.data.api.NewsAPIService
import com.example.topnews.data.model.News
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService
) : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<News> {
        return newsAPIService.getTopHeadlines(country, page)
    }

    override suspend fun getHeadlinesByCategory(
        country: String,
        page: Int,
        category: String
    ): Response<News> {
        return newsAPIService.getHeadlinesByCategory(country, page, category)
    }

    override suspend fun getSearchedHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<News> {
        return newsAPIService.getSearchedTopHeadlines(country, searchQuery, page)
    }
}