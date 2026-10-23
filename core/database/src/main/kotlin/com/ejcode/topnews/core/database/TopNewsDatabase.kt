package com.ejcode.topnews.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ejcode.topnews.core.database.dao.HeadlineDao
import com.ejcode.topnews.core.database.dao.SavedArticleDao
import com.ejcode.topnews.core.database.entity.HeadlineEntity
import com.ejcode.topnews.core.database.entity.SavedArticleEntity

@Database(
    entities = [HeadlineEntity::class, SavedArticleEntity::class],
    version = 1,
    exportSchema = true
)
abstract class TopNewsDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
    abstract fun savedArticleDao(): SavedArticleDao
}
