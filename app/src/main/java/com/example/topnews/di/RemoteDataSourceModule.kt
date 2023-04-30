package com.example.topnews.di

import com.example.topnews.data.NewsRemoteDataSource
import com.example.topnews.data.NewsRemoteDataSourceImpl
import com.example.topnews.data.api.NewsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsAPIService: NewsAPIService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}