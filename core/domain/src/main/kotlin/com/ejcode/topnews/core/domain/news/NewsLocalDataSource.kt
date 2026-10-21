package com.ejcode.topnews.core.domain.news

import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    fun observeHeadlines(category: NewsCategory?): Flow<List<Article>>
    fun observeArticle(url: String): Flow<Article?>
    suspend fun replaceHeadlines(
        category: NewsCategory?,
        articles: List<Article>
    ): EmptyResult<DataError.Local>
}
