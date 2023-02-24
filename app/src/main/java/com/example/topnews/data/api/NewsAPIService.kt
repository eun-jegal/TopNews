package com.example.topnews.data.api

import com.example.topnews.BuildConfig
import com.example.topnews.data.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val URL_PATH = "v2/top-headlines"
interface NewsAPIService {
    @GET(URL_PATH)
    suspend fun getTopHeadlines(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<News>

    @GET(URL_PATH)
    suspend fun getHeadlinesByCategory(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("category")
        category: String,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<News>

    @GET(URL_PATH)
    suspend fun getSearchedTopHeadlines(
        @Query("country")
        country: String,
        @Query("q")
        searchQuery: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<News>
}