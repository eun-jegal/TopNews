package com.ejcode.topnews.core.data.news.mappers

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import com.ejcode.topnews.core.domain.model.NewsCategory
import com.ejcode.topnews.core.domain.model.sampleArticle
import org.junit.jupiter.api.Test
import kotlin.time.Instant

class ArticleEntityMappersTest {

    private val article = sampleArticle(
        description = null,
        imageUrl = null,
        publishedAt = Instant.parse("2026-10-21T09:15:30.123Z")
    )

    @Test
    fun `headline entity round-trips to the same article`() {
        val entity = article.toHeadlineEntity(listKey = "trending", position = 3)

        assertThat(entity.listKey).isEqualTo("trending")
        assertThat(entity.position).isEqualTo(3)
        assertThat(entity.toArticle()).isEqualTo(article)
    }

    @Test
    fun `saved article entity round-trips to the same article`() {
        val savedAt = Instant.parse("2026-10-22T12:00:00Z")

        val entity = article.toSavedArticleEntity(savedAt)

        assertThat(entity.savedAtMillis).isEqualTo(savedAt.toEpochMilliseconds())
        assertThat(entity.toArticle()).isEqualTo(article)
    }

    @Test
    fun `null category maps to the trending list key`() {
        assertThat((null as NewsCategory?).toListKey()).isEqualTo("trending")
    }

    @Test
    fun `category maps to its own list key`() {
        assertThat(NewsCategory.SPORTS.toListKey()).isEqualTo("category:sports")
        assertThat(NewsCategory.GENERAL.toListKey()).isNotEqualTo((null as NewsCategory?).toListKey())
    }
}
