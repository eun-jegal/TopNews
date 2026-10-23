package com.ejcode.topnews.core.data.news.mappers

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.prop
import com.ejcode.topnews.core.data.news.remote.ArticleDto
import com.ejcode.topnews.core.data.news.remote.SourceDto
import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import org.junit.jupiter.api.Test
import kotlin.time.Instant

class ArticleDtoMappersTest {

    private fun dto(
        url: String? = "https://example.com/a",
        title: String? = "Headline",
        publishedAt: String? = "2026-10-21T09:00:00Z",
        content: String? = "Body",
        description: String? = "Description"
    ) = ArticleDto(
        source = SourceDto(id = "example", name = "Example News"),
        author = "Jane Doe",
        title = title,
        description = description,
        url = url,
        urlToImage = "https://example.com/a.jpg",
        publishedAt = publishedAt,
        content = content
    )

    @Test
    fun `maps all fields`() {
        assertThat(dto().toArticle()).isEqualTo(
            Article(
                url = "https://example.com/a",
                title = "Headline",
                description = "Description",
                content = "Body",
                author = "Jane Doe",
                sourceName = "Example News",
                imageUrl = "https://example.com/a.jpg",
                publishedAt = Instant.parse("2026-10-21T09:00:00Z")
            )
        )
    }

    @Test
    fun `strips truncation suffix from content`() {
        val article = dto(content = "The first 200 chars of the story… [+2154 chars]").toArticle()

        assertThat(article).isNotNull().prop(Article::content)
            .isEqualTo("The first 200 chars of the story…")
    }

    @Test
    fun `blank optional fields become null`() {
        val article = dto(content = "  ", description = "").toArticle()

        assertThat(article).isNotNull().prop(Article::content).isNull()
        assertThat(article).isNotNull().prop(Article::description).isNull()
    }

    @Test
    fun `missing url, title or unparsable date yields null`() {
        assertThat(dto(url = null).toArticle()).isNull()
        assertThat(dto(title = " ").toArticle()).isNull()
        assertThat(dto(publishedAt = "yesterday").toArticle()).isNull()
    }

    @Test
    fun `toArticles drops removed articles and duplicate urls`() {
        val articles = listOf(
            dto(url = "https://example.com/1", title = "First"),
            dto(url = "https://removed.com", title = "[Removed]"),
            dto(url = "https://example.com/1", title = "First again"),
            dto(url = "https://example.com/2", title = "Second")
        ).toArticles()

        assertThat(articles.map { it.title }).containsExactly("First", "Second")
    }

    @Test
    fun `category api value is lowercase name`() {
        assertThat(NewsCategory.TECHNOLOGY.toApiValue()).isEqualTo("technology")
    }
}
