package com.ejcode.topnews.core.data.news.mappers

import com.ejcode.topnews.core.database.entity.HeadlineEntity
import com.ejcode.topnews.core.database.entity.SavedArticleEntity
import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import kotlin.time.Instant

private const val TRENDING_LIST_KEY = "trending"

/** The cache list a category's headlines are stored under; null is the trending feed. */
fun NewsCategory?.toListKey(): String = this?.let { "category:${it.name.lowercase()}" } ?: TRENDING_LIST_KEY

fun HeadlineEntity.toArticle(): Article = Article(
    url = url,
    title = title,
    description = description,
    content = content,
    author = author,
    sourceName = sourceName,
    imageUrl = imageUrl,
    publishedAt = Instant.fromEpochMilliseconds(publishedAtMillis)
)

fun Article.toHeadlineEntity(listKey: String, position: Int): HeadlineEntity = HeadlineEntity(
    listKey = listKey,
    url = url,
    position = position,
    title = title,
    description = description,
    content = content,
    author = author,
    sourceName = sourceName,
    imageUrl = imageUrl,
    publishedAtMillis = publishedAt.toEpochMilliseconds()
)

fun SavedArticleEntity.toArticle(): Article = Article(
    url = url,
    title = title,
    description = description,
    content = content,
    author = author,
    sourceName = sourceName,
    imageUrl = imageUrl,
    publishedAt = Instant.fromEpochMilliseconds(publishedAtMillis)
)

fun Article.toSavedArticleEntity(savedAt: Instant): SavedArticleEntity = SavedArticleEntity(
    url = url,
    title = title,
    description = description,
    content = content,
    author = author,
    sourceName = sourceName,
    imageUrl = imageUrl,
    publishedAtMillis = publishedAt.toEpochMilliseconds(),
    savedAtMillis = savedAt.toEpochMilliseconds()
)
