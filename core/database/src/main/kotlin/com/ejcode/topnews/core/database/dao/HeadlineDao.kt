package com.ejcode.topnews.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ejcode.topnews.core.database.entity.HeadlineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeadlineDao {

    @Query("SELECT * FROM headlines WHERE listKey = :listKey ORDER BY position")
    fun observeByListKey(listKey: String): Flow<List<HeadlineEntity>>

    @Query("SELECT * FROM headlines WHERE url = :url LIMIT 1")
    fun observeByUrl(url: String): Flow<HeadlineEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<HeadlineEntity>)

    @Query("DELETE FROM headlines WHERE listKey = :listKey")
    suspend fun deleteByListKey(listKey: String)

    // One transaction, so observers never see the list empty between delete and insert
    @Transaction
    suspend fun replaceList(listKey: String, entities: List<HeadlineEntity>) {
        deleteByListKey(listKey)
        insertAll(entities)
    }
}
