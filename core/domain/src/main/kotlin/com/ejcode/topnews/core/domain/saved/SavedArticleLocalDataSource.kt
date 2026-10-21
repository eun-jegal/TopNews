package com.ejcode.topnews.core.domain.saved

import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

/** Bookmarked articles, stored independently of the headline cache so they stay available offline. */
interface SavedArticleLocalDataSource {
    fun observeSavedArticles(): Flow<List<Article>>
    fun observeSavedArticle(url: String): Flow<Article?>
    suspend fun save(article: Article): EmptyResult<DataError.Local>
    suspend fun remove(url: String): EmptyResult<DataError.Local>
}
