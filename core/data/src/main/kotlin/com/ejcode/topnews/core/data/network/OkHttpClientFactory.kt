package com.ejcode.topnews.core.data.network

import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpClientFactory {
    fun create(
        interceptors: List<Interceptor>,
        isDebug: Boolean,
        authenticator: Authenticator? = null
    ): OkHttpClient = OkHttpClient.Builder()
        .apply { interceptors.forEach(::addInterceptor) }
        .apply { authenticator?.let(::authenticator) }
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = if (isDebug) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                // Keep credentials out of Logcat
                redactHeader("X-Api-Key")
                redactHeader("Authorization")
            }
        )
        .build()
}
