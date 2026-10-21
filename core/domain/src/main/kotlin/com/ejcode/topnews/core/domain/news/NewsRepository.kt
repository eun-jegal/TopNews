package com.ejcode.topnews.core.domain.news

import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

/**
 * Offline-first access to headlines: the local cache is the source of truth and
 * [refreshHeadlines] updates it from the network.
 *
 * A `null` category means trending headlines (the Feed).
 */
interface NewsRepository {
    fun observeHeadlines(category: NewsCategory?): Flow<List<Article>>
    suspend fun refreshHeadlines(category: NewsCategory?): EmptyResult<DataError.Network>
    fun observeArticle(url: String): Flow<Article?>
}
