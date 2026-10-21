package com.ejcode.topnews.core.domain.model

import kotlin.time.Instant

fun sampleArticle(
    url: String = "https://example.com/article-1",
    title: String = "Sample headline",
    description: String? = "Sample description",
    content: String? = "Sample content",
    author: String? = "Jane Doe",
    sourceName: String = "Example News",
    imageUrl: String? = "https://example.com/image-1.jpg",
    publishedAt: Instant = Instant.parse("2026-10-21T09:00:00Z")
): Article = Article(
    url = url,
    title = title,
    description = description,
    content = content,
    author = author,
    sourceName = sourceName,
    imageUrl = imageUrl,
    publishedAt = publishedAt
)

/** [count] distinct articles with unique URLs and titles. */
fun sampleArticles(count: Int, prefix: String = "article"): List<Article> = (1..count).map {
    sampleArticle(
        url = "https://example.com/$prefix-$it",
        title = "Sample headline $prefix $it",
        imageUrl = "https://example.com/$prefix-$it.jpg"
    )
}
