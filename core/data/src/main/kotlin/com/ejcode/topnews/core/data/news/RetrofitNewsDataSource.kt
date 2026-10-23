package com.ejcode.topnews.core.data.news

import com.ejcode.topnews.core.data.network.safeCall
import com.ejcode.topnews.core.data.news.mappers.toApiValue
import com.ejcode.topnews.core.data.news.mappers.toArticles
import com.ejcode.topnews.core.data.news.remote.NewsApi
import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import com.ejcode.topnews.core.domain.news.NewsRemoteDataSource
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.Result
import com.ejcode.topnews.core.domain.util.map
import javax.inject.Inject

class RetrofitNewsDataSource @Inject constructor(
    private val api: NewsApi
) : NewsRemoteDataSource {
    override suspend fun fetchTopHeadlines(
        category: NewsCategory?
    ): Result<List<Article>, DataError.Network> {
        return safeCall { api.getTopHeadlines(category = category?.toApiValue()) }
            .map { it.articles.orEmpty().toArticles() }
    }
}
