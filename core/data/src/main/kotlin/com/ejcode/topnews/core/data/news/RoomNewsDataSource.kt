package com.ejcode.topnews.core.data.news

import android.database.sqlite.SQLiteFullException
import com.ejcode.topnews.core.data.news.mappers.toArticle
import com.ejcode.topnews.core.data.news.mappers.toHeadlineEntity
import com.ejcode.topnews.core.data.news.mappers.toListKey
import com.ejcode.topnews.core.database.dao.HeadlineDao
import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import com.ejcode.topnews.core.domain.news.NewsLocalDataSource
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.EmptyResult
import com.ejcode.topnews.core.domain.util.Result
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomNewsDataSource @Inject constructor(
    private val dao: HeadlineDao
) : NewsLocalDataSource {

    override fun observeHeadlines(category: NewsCategory?): Flow<List<Article>> =
        dao.observeByListKey(category.toListKey()).map { entities -> entities.map { it.toArticle() } }

    override fun observeArticle(url: String): Flow<Article?> =
        dao.observeByUrl(url).map { it?.toArticle() }

    override suspend fun replaceHeadlines(
        category: NewsCategory?,
        articles: List<Article>
    ): EmptyResult<DataError.Local> {
        val listKey = category.toListKey()
        return try {
            dao.replaceList(
                listKey = listKey,
                entities = articles.mapIndexed { index, article ->
                    article.toHeadlineEntity(listKey, position = index)
                }
            )
            Result.Success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}
