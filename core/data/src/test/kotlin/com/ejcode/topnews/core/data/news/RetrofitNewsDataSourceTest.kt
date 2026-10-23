package com.ejcode.topnews.core.data.news

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNull
import assertk.assertions.prop
import com.ejcode.topnews.core.data.network.RetrofitFactory
import com.ejcode.topnews.core.data.news.remote.NewsApi
import com.ejcode.topnews.core.domain.model.Article
import com.ejcode.topnews.core.domain.model.NewsCategory
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.Result
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RetrofitNewsDataSourceTest {
    private lateinit var server: MockWebServer
    private lateinit var dataSource: RetrofitNewsDataSource

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
        val api = RetrofitFactory.create(server.url("/v2/").toString(), OkHttpClient())
            .create(NewsApi::class.java)
        dataSource = RetrofitNewsDataSource(api)
    }

    @AfterEach
    fun tearDown() {
        server.close()
    }

    @Test
    fun `parses and maps headlines`() = runTest {
        server.enqueue(MockResponse(code = 200, body = HEADLINES_JSON))

        val result = dataSource.fetchTopHeadlines(category = null)

        assertThat(result).isInstanceOf<Result.Success<List<Article>>>()
            .prop(Result.Success<List<Article>>::data)
            .transform { articles -> articles.map { it.title } }
            .containsExactly("Markets rally", "New phone launched")
    }

    @Test
    fun `trending request omits category`() = runTest {
        server.enqueue(MockResponse(code = 200, body = HEADLINES_JSON))

        dataSource.fetchTopHeadlines(category = null)

        val url = server.takeRequest().url
        assertThat(url.encodedPath).isEqualTo("/v2/top-headlines")
        assertThat(url.queryParameter("category")).isNull()
        assertThat(url.queryParameter("country")).isEqualTo("us")
        assertThat(url.queryParameter("pageSize")).isEqualTo("50")
    }

    @Test
    fun `category request sends category query`() = runTest {
        server.enqueue(MockResponse(code = 200, body = HEADLINES_JSON))

        dataSource.fetchTopHeadlines(category = NewsCategory.SPORTS)

        assertThat(server.takeRequest().url.queryParameter("category")).isEqualTo("sports")
    }

    @Test
    fun `401 maps to UNAUTHORIZED`() = runTest {
        server.enqueue(MockResponse(code = 401, body = ERROR_JSON))

        assertThat(dataSource.fetchTopHeadlines(null))
            .isEqualTo(Result.Error(DataError.Network.UNAUTHORIZED))
    }

    @Test
    fun `429 maps to TOO_MANY_REQUESTS`() = runTest {
        server.enqueue(MockResponse(code = 429, body = ERROR_JSON))

        assertThat(dataSource.fetchTopHeadlines(null))
            .isEqualTo(Result.Error(DataError.Network.TOO_MANY_REQUESTS))
    }

    @Test
    fun `malformed body maps to SERIALIZATION`() = runTest {
        server.enqueue(MockResponse(code = 200, body = """{"status":"ok","articles":"""))

        assertThat(dataSource.fetchTopHeadlines(null))
            .isEqualTo(Result.Error(DataError.Network.SERIALIZATION))
    }

    private companion object {
        val HEADLINES_JSON = """
            {
              "status": "ok",
              "totalResults": 3,
              "articles": [
                {
                  "source": { "id": null, "name": "Example Finance" },
                  "author": "Jane Doe",
                  "title": "Markets rally",
                  "description": "Stocks rose sharply.",
                  "url": "https://example.com/markets",
                  "urlToImage": "https://example.com/markets.jpg",
                  "publishedAt": "2026-10-21T09:00:00Z",
                  "content": "Stocks rose sharply on Tuesday… [+1820 chars]"
                },
                {
                  "source": { "id": null, "name": "[Removed]" },
                  "author": null,
                  "title": "[Removed]",
                  "description": "[Removed]",
                  "url": "https://removed.com",
                  "urlToImage": null,
                  "publishedAt": "2026-10-21T08:00:00Z",
                  "content": "[Removed]"
                },
                {
                  "source": { "id": "tech", "name": "Example Tech" },
                  "author": null,
                  "title": "New phone launched",
                  "description": null,
                  "url": "https://example.com/phone",
                  "urlToImage": null,
                  "publishedAt": "2026-10-21T07:30:00Z",
                  "content": null,
                  "extraField": "ignored"
                }
              ]
            }
        """.trimIndent()

        val ERROR_JSON = """
            {"status":"error","code":"apiKeyInvalid","message":"Your API key is invalid."}
        """.trimIndent()
    }
}
