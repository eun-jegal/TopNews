package com.ejcode.topnews.core.data.news.mappers

import com.ejcode.topnews.core.data.news.remote.ArticleDto
import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import kotlin.time.Instant

// NewsAPI marks taken-down articles with this placeholder title
private const val REMOVED_PLACEHOLDER = "[Removed]"

// Free-tier content is truncated and ends with e.g. " [+2154 chars]"
private val TRUNCATION_SUFFIX = Regex("""\s*\[\+\d+ chars]$""")

/** Returns null when the DTO lacks the fields an [Article] requires (url, title, publish time). */
fun ArticleDto.toArticle(): Article? {
    val url = url?.takeIf { it.isNotBlank() } ?: return null
    val title = title?.takeIf { it.isNotBlank() } ?: return null
    val publishedAt = publishedAt?.let(Instant::parseOrNull) ?: return null
    return Article(
        url = url,
        title = title,
        description = description?.takeIf { it.isNotBlank() },
        content = content?.replace(TRUNCATION_SUFFIX, "")?.takeIf { it.isNotBlank() },
        author = author?.takeIf { it.isNotBlank() },
        sourceName = source?.name.orEmpty(),
        imageUrl = urlToImage?.takeIf { it.isNotBlank() },
        publishedAt = publishedAt
    )
}

fun List<ArticleDto>.toArticles(): List<Article> = this
    .filterNot { it.title == REMOVED_PLACEHOLDER }
    .mapNotNull { it.toArticle() }
    .distinctBy { it.url }

fun NewsCategory.toApiValue(): String = name.lowercase()
