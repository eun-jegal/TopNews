package com.example.topnews.data

import com.example.topnews.data.model.News
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(
        country: String,
        page: Int
    ): Response<News>

    suspend fun getHeadlinesByCategory(
        country: String,
        page: Int,
        category: String
    ): Response<News>

    suspend fun getSearchedHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<News>
}