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

class FakeNewsLocalDataSource : NewsLocalDataSource {
    private val headlines = MutableStateFlow<Map<NewsCategory?, List<Article>>>(emptyMap())

    /** When set, every write fails with this error. */
    var error: DataError.Local? = null

    /** Current cached list for [category], for assertions. */
    fun cachedHeadlines(category: NewsCategory?): List<Article> = headlines.value[category].orEmpty()

    override fun observeHeadlines(category: NewsCategory?): Flow<List<Article>> =
        headlines.map { it[category].orEmpty() }

    override fun observeArticle(url: String): Flow<Article?> =
        headlines.map { all -> all.values.flatten().firstOrNull { it.url == url } }

    override suspend fun replaceHeadlines(
        category: NewsCategory?,
        articles: List<Article>
    ): EmptyResult<DataError.Local> {
        error?.let { return Result.Error(it) }
        headlines.update { it + (category to articles) }
        return Result.Success(Unit)
    }
}
