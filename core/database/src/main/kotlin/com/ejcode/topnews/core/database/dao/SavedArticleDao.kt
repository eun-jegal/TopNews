package com.ejcode.topnews.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ejcode.topnews.core.database.entity.SavedArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedArticleDao {

    @Query("SELECT * FROM saved_articles ORDER BY savedAtMillis DESC")
    fun observeAll(): Flow<List<SavedArticleEntity>>

    @Query("SELECT * FROM saved_articles WHERE url = :url")
    fun observeByUrl(url: String): Flow<SavedArticleEntity?>

    @Upsert
    suspend fun upsert(entity: SavedArticleEntity)

    @Query("DELETE FROM saved_articles WHERE url = :url")
    suspend fun deleteByUrl(url: String)
}
