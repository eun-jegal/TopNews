package com.ejcode.topnews.core.data.network

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.ejcode.topnews.core.data.BuildConfig
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ApiKeyInterceptorTest {
    private lateinit var server: MockWebServer
    private lateinit var client: OkHttpClient

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
        client = OkHttpClientFactory.create(
            interceptors = listOf(ApiKeyInterceptor()),
            isDebug = false
        )
    }

    @AfterEach
    fun tearDown() {
        server.close()
    }

    @Test
    fun `request carries the API key header`() {
        server.enqueue(MockResponse(code = 200))

        client.newCall(Request(server.url("/top-headlines"))).execute().close()

        val recorded = server.takeRequest()
        assertThat(recorded.headers["X-Api-Key"]).isEqualTo(BuildConfig.NEWS_API_KEY)
    }

    @Test
    fun `API key header replaces any existing value`() {
        server.enqueue(MockResponse(code = 200))
        val request = Request.Builder()
            .url(server.url("/top-headlines"))
            .header("X-Api-Key", "stale-key")
            .build()

        client.newCall(request).execute().close()

        assertThat(server.takeRequest().headers.values("X-Api-Key"))
            .isEqualTo(listOf(BuildConfig.NEWS_API_KEY))
    }

    @Test
    fun `API key is never added to the URL query`() {
        server.enqueue(MockResponse(code = 200))

        client.newCall(Request(server.url("/top-headlines?country=us"))).execute().close()

        assertThat(server.takeRequest().url.queryParameter("apiKey")).isNull()
    }
}
