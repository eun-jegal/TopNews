package com.example.topnews.data.repository

import com.example.topnews.data.NewsRemoteDataSource
import com.example.topnews.data.model.News
import com.example.topnews.domain.TestUtil.errorResponse
import com.example.topnews.domain.TestUtil.successResponse
import retrofit2.Response

class FakeRemoteDataSource : NewsRemoteDataSource {

    override suspend fun getTopHeadlines(country: String, page: Int): Response<News> {
        return when (country) {
            "us" -> successResponse
            else -> errorResponse
        }
    }

    override suspend fun getHeadlinesByCategory(
        country: String,
        page: Int,
        category: String
    ): Response<News> {
        return when (category) {
            "General" -> successResponse
            else -> errorResponse
        }
    }

    override suspend fun getSearchedHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<News> {
        return when (searchQuery) {
            "sports" -> successResponse
            else -> errorResponse
        }
    }
}