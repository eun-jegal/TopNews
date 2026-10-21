package com.ejcode.topnews.core.data.di

import com.ejcode.topnews.core.data.BuildConfig
import com.ejcode.topnews.core.data.network.ApiKeyInterceptor
import com.ejcode.topnews.core.data.network.OkHttpClientFactory
import com.ejcode.topnews.core.data.network.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient =
        OkHttpClientFactory.create(
            interceptors = listOf(apiKeyInterceptor),
            isDebug = BuildConfig.DEBUG
        )

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        RetrofitFactory.create(BuildConfig.BASE_URL, okHttpClient)
}
