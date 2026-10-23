package com.ejcode.topnews.core.data.news.remote

import kotlinx.serialization.Serializable

@Serializable
data class TopHeadlinesDto(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<ArticleDto>? = null
)

@Serializable
data class ArticleDto(
    val source: SourceDto? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
)

@Serializable
data class SourceDto(
    val id: String? = null,
    val name: String? = null
)
