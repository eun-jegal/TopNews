package com.ejcode.topnews.core.data.news

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.ejcode.topnews.core.domain.model.NewsCategory
import com.ejcode.topnews.core.domain.model.sampleArticles
import com.ejcode.topnews.core.domain.news.FakeNewsLocalDataSource
import com.ejcode.topnews.core.domain.news.FakeNewsRemoteDataSource
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.Result
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OfflineFirstNewsRepositoryTest {
    private lateinit var local: FakeNewsLocalDataSource
    private lateinit var remote: FakeNewsRemoteDataSource
    private lateinit var repository: OfflineFirstNewsRepository

    private val cached = sampleArticles(2, prefix = "cached")
    private val fresh = sampleArticles(3, prefix = "fresh")

    @BeforeEach
    fun setUp() {
        local = FakeNewsLocalDataSource()
        remote = FakeNewsRemoteDataSource()
        repository = OfflineFirstNewsRepository(local, remote)
    }

    @Test
    fun `successful refresh replaces the cache`() = runTest {
        local.replaceHeadlines(null, cached)
        remote.headlines = mapOf(null to fresh)

        val result = repository.refreshHeadlines(category = null)

        assertThat(result).isEqualTo(Result.Success(Unit))
        assertThat(local.cachedHeadlines(null)).isEqualTo(fresh)
    }

    @Test
    fun `refresh fetches the requested category only`() = runTest {
        remote.headlines = mapOf(NewsCategory.SPORTS to fresh)

        repository.refreshHeadlines(NewsCategory.SPORTS)

        assertThat(remote.fetchedCategories).containsExactly(NewsCategory.SPORTS)
        assertThat(local.cachedHeadlines(NewsCategory.SPORTS)).isEqualTo(fresh)
        assertThat(local.cachedHeadlines(null)).isEmpty()
    }

    @Test
    fun `network error keeps the cache and returns the error`() = runTest {
        local.replaceHeadlines(null, cached)
        remote.error = DataError.Network.NO_INTERNET

        val result = repository.refreshHeadlines(category = null)

        assertThat(result).isEqualTo(Result.Error(DataError.Network.NO_INTERNET))
        assertThat(local.cachedHeadlines(null)).isEqualTo(cached)
    }

    @Test
    fun `cache write error is returned`() = runTest {
        remote.headlines = mapOf(null to fresh)
        local.error = DataError.Local.DISK_FULL

        val result = repository.refreshHeadlines(category = null)

        assertThat(result).isEqualTo(Result.Error(DataError.Local.DISK_FULL))
    }

    @Test
    fun `observeHeadlines emits cached data and updates after refresh`() = runTest {
        local.replaceHeadlines(null, cached)
        remote.headlines = mapOf(null to fresh)

        repository.observeHeadlines(null).test {
            assertThat(awaitItem()).isEqualTo(cached)

            repository.refreshHeadlines(null)

            assertThat(awaitItem()).isEqualTo(fresh)
        }
    }

    @Test
    fun `observeArticle emits the cached article or null`() = runTest {
        local.replaceHeadlines(null, cached)

        repository.observeArticle(cached.first().url).test {
            assertThat(awaitItem()).isEqualTo(cached.first())
        }
        repository.observeArticle("https://example.com/unknown").test {
            assertThat(awaitItem()).isNull()
        }
    }
}
