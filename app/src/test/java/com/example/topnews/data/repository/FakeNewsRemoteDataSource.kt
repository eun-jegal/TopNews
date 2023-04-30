package com.example.topnews.data.repository

import com.example.topnews.data.NewsRemoteDataSource
import com.example.topnews.data.model.News
import retrofit2.Response

class FakeNewsRemoteDataSource: NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<News> {
        return successResponse
    }

    override suspend fun getHeadlinesByCategory(
        country: String,
        page: Int,
        category: String
    ): Response<News> {
        return if (category == "science") successResponse else errorResponse
    }

    override suspend fun getSearchedHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<News> {
        return if (searchQuery.isNotEmpty()) successResponse else errorResponse
    }
}