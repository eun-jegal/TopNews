package com.ejcode.topnews.core.domain.news

import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.Result

interface NewsRemoteDataSource {
    suspend fun fetchTopHeadlines(category: NewsCategory?): Result<List<Article>, DataError.Network>
}
