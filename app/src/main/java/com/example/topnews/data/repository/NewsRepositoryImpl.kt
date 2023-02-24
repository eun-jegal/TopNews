package com.example.topnews.data.repository

import com.example.topnews.data.NewsLocalDataSource
import com.example.topnews.data.NewsRemoteDataSource
import com.example.topnews.data.model.Article
import com.example.topnews.data.model.News
import com.example.topnews.data.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource
) : NewsRepository {
    override suspend fun getTopHeadlines(country: String, page: Int): NetworkResult<News> {
        return responseToResult(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getHeadlinesByCategory(
        country: String,
        page: Int,
        category: String
    ): NetworkResult<News> {
        return responseToResult(
            newsRemoteDataSource.getHeadlinesByCategory(
                country,
                page,
                category
            )
        )
    }

    override suspend fun getSearchedTopHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): NetworkResult<News> {
        return responseToResult(
            newsRemoteDataSource.getSearchedHeadlines(
                country,
                searchQuery,
                page
            )
        )
    }

    override suspend fun saveArticle(article: Article) {
        newsLocalDataSource.saveArticle(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsLocalDataSource.deleteArticle(article)
    }

    override fun getAllArticles(): Flow<List<Article>> {
        return newsLocalDataSource.getAllArticles()
    }

    override fun deleteAllArticles() {
        newsLocalDataSource.deleteAllArticles()
    }

    private fun responseToResult(response: Response<News>): NetworkResult<News> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return NetworkResult.Success(result)
            }
        }
        return NetworkResult.Error(response.message())
    }
}