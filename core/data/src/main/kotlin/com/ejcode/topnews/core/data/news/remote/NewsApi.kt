package com.ejcode.topnews.core.data.news.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    // A null category is omitted from the query, which returns trending headlines
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String?,
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: Int = 50
    ): Response<TopHeadlinesDto>
}
