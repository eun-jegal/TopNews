package com.ejcode.topnews.core.domain.saved

import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.EmptyResult
import com.ejcode.topnews.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeSavedArticleLocalDataSource : SavedArticleLocalDataSource {
    // Newest first, matching the real data source's ordering
    private val saved = MutableStateFlow<List<Article>>(emptyList())

    /** When set, every write fails with this error. */
    var error: DataError.Local? = null

    val savedArticles: List<Article> get() = saved.value

    override fun observeSavedArticles(): Flow<List<Article>> = saved.asStateFlow()

    override fun observeSavedArticle(url: String): Flow<Article?> =
        saved.map { articles -> articles.firstOrNull { it.url == url } }

    override suspend fun save(article: Article): EmptyResult<DataError.Local> {
        error?.let { return Result.Error(it) }
        saved.update { articles -> listOf(article) + articles.filterNot { it.url == article.url } }
        return Result.Success(Unit)
    }

    override suspend fun remove(url: String): EmptyResult<DataError.Local> {
        error?.let { return Result.Error(it) }
        saved.update { articles -> articles.filterNot { it.url == url } }
        return Result.Success(Unit)
    }
}
