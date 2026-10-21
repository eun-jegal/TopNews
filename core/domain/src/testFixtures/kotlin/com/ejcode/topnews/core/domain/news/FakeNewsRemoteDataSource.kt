package com.ejcode.topnews.core.domain.news

import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.Result

class FakeNewsRemoteDataSource : NewsRemoteDataSource {
    /** What each fetch returns, keyed by category (`null` = trending). */
    var headlines: Map<NewsCategory?, List<Article>> = emptyMap()

    /** When set, every fetch fails with this error. */
    var error: DataError.Network? = null

    val fetchedCategories = mutableListOf<NewsCategory?>()

    override suspend fun fetchTopHeadlines(
        category: NewsCategory?
    ): Result<List<Article>, DataError.Network> {
        fetchedCategories += category
        error?.let { return Result.Error(it) }
        return Result.Success(headlines[category].orEmpty())
    }
}
