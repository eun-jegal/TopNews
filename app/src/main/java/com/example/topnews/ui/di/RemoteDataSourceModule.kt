package com.example.topnews.ui.di

import com.example.topnews.data.NewsRemoteDataSource
import com.example.topnews.data.NewsRemoteDataSourceImpl
import com.example.topnews.data.api.NewsAPIService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsAPIService: NewsAPIService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}