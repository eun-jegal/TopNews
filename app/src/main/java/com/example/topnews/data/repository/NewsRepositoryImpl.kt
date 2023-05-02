package com.example.topnews.data.repository

import com.example.topnews.data.NewsLocalDataSource
import com.example.topnews.data.NewsRemoteDataSource
import com.example.topnews.data.model.Article
import com.example.topnews.data.model.News
import com.example.topnews.data.other.Resource
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource
) : NewsRepository {
    override suspend fun getTopHeadlines(country: String, page: Int): Resource<News> {
        return responseToResult(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getHeadlinesByCategory(
        country: String,
        page: Int,
        category: String
    ): Resource<News> {
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
    ): Resource<News> {
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

    override fun getAllArticles(): List<Article> {
        return newsLocalDataSource.getAllArticles()
    }

    override fun deleteAllArticles() {
        newsLocalDataSource.deleteAllArticles()
    }

    private fun responseToResult(response: Response<News>): Resource<News> {
        return try {
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    Resource.Success(result)
                } ?: Resource.Error("Something went wrong")
            } else {
                Resource.Error("Something went wrong")
            }
        } catch (e: Exception) {
            Resource.Error("Could not load data. Please check your internet connection!")
        }
    }
}