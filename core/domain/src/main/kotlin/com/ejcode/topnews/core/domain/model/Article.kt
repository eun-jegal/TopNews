package com.ejcode.topnews.core.domain.model

import kotlin.time.Instant

/**
 * A news article. NewsAPI has no article IDs, so [url] is the identity.
 */
data class Article(
    val url: String,
    val title: String,
    val description: String?,
    val content: String?,
    val author: String?,
    val sourceName: String,
    val imageUrl: String?,
    val publishedAt: Instant
)
