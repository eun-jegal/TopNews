package com.ejcode.topnews.core.database.entity

import androidx.room.Entity
import androidx.room.Index

/**
 * A cached headline belonging to one list (trending or a category).
 * The same article can appear in several lists, so the key is ([listKey], [url]).
 */
@Entity(
    tableName = "headlines",
    primaryKeys = ["listKey", "url"],
    indices = [Index("url")]
)
data class HeadlineEntity(
    val listKey: String,
    val url: String,
    val position: Int,
    val title: String,
    val description: String?,
    val content: String?,
    val author: String?,
    val sourceName: String,
    val imageUrl: String?,
    val publishedAtMillis: Long
)
