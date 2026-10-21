package com.ejcode.topnews.core.data.network

import com.ejcode.topnews.core.data.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.Interceptor

/**
 * Sends the NewsAPI key in the `X-Api-Key` header on every request.
 * Never put it in the URL query — query strings end up in logs.
 */
@Singleton
class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder()
            .header("X-Api-Key", BuildConfig.NEWS_API_KEY)
            .build()
        return chain.proceed(request)
    }
}
