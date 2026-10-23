package com.ejcode.topnews.core.data.news

import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import com.ejcode.topnews.core.domain.news.NewsLocalDataSource
import com.ejcode.topnews.core.domain.news.NewsRemoteDataSource
import com.ejcode.topnews.core.domain.news.NewsRepository
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.EmptyResult
import com.ejcode.topnews.core.domain.util.Result
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/** Room is the source of truth; the network only ever writes into it. */
class OfflineFirstNewsRepository @Inject constructor(
    private val localDataSource: NewsLocalDataSource,
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override fun observeHeadlines(category: NewsCategory?): Flow<List<Article>> =
        localDataSource.observeHeadlines(category)

    override fun observeArticle(url: String): Flow<Article?> =
        localDataSource.observeArticle(url)

    // On a network error the cache is left untouched, so offline users keep the last list
    override suspend fun refreshHeadlines(category: NewsCategory?): EmptyResult<DataError> {
        return when (val remote = remoteDataSource.fetchTopHeadlines(category)) {
            is Result.Success -> localDataSource.replaceHeadlines(category, remote.data)
            is Result.Error -> remote
        }
    }
}
