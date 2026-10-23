package com.ejcode.topnews.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/** A bookmarked article. Kept apart from [HeadlineEntity] so cache refreshes never remove it. */
@Entity(tableName = "saved_articles")
data class SavedArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val description: String?,
    val content: String?,
    val author: String?,
    val sourceName: String,
    val imageUrl: String?,
    val publishedAtMillis: Long,
    val savedAtMillis: Long
)
