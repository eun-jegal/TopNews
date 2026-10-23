package com.ejcode.topnews.core.data.saved

import android.database.sqlite.SQLiteFullException
import com.ejcode.topnews.core.data.news.mappers.toArticle
import com.ejcode.topnews.core.data.news.mappers.toSavedArticleEntity
import com.ejcode.topnews.core.database.dao.SavedArticleDao
import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.saved.SavedArticleLocalDataSource
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.EmptyResult
import com.ejcode.topnews.core.domain.util.Result
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Clock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomSavedArticleDataSource @Inject constructor(
    private val dao: SavedArticleDao
) : SavedArticleLocalDataSource {

    override fun observeSavedArticles(): Flow<List<Article>> =
        dao.observeAll().map { entities -> entities.map { it.toArticle() } }

    override fun observeSavedArticle(url: String): Flow<Article?> =
        dao.observeByUrl(url).map { it?.toArticle() }

    override suspend fun save(article: Article): EmptyResult<DataError.Local> = runCatchingLocal {
        dao.upsert(article.toSavedArticleEntity(savedAt = Clock.System.now()))
    }

    override suspend fun remove(url: String): EmptyResult<DataError.Local> = runCatchingLocal {
        dao.deleteByUrl(url)
    }

    private inline fun runCatchingLocal(block: () -> Unit): EmptyResult<DataError.Local> =
        try {
            block()
            Result.Success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
}
