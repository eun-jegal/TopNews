package com.ejcode.topnews.core.domain.news

import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.EmptyResult
import com.ejcode.topnews.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

/**
 * Behaves like the offline-first repository: observers see the cache, and a successful
 * refresh replaces the cached list with [remoteHeadlines]. A failed refresh leaves the cache as is.
 */
class FakeNewsRepository : NewsRepository {
    private val cache = MutableStateFlow<Map<NewsCategory?, List<Article>>>(emptyMap())

    /** What a refresh "downloads", keyed by category (`null` = trending). */
    var remoteHeadlines: Map<NewsCategory?, List<Article>> = emptyMap()

    /** When set, every refresh fails with this error. */
    var refreshError: DataError? = null

    val refreshedCategories = mutableListOf<NewsCategory?>()

    fun setCachedHeadlines(category: NewsCategory?, articles: List<Article>) {
        cache.update { it + (category to articles) }
    }

    override fun observeHeadlines(category: NewsCategory?): Flow<List<Article>> =
        cache.map { it[category].orEmpty() }

    override suspend fun refreshHeadlines(category: NewsCategory?): EmptyResult<DataError> {
        refreshedCategories += category
        refreshError?.let { return Result.Error(it) }
        remoteHeadlines[category]?.let { articles -> setCachedHeadlines(category, articles) }
        return Result.Success(Unit)
    }

    override fun observeArticle(url: String): Flow<Article?> =
        cache.map { all -> all.values.flatten().firstOrNull { it.url == url } }
}
