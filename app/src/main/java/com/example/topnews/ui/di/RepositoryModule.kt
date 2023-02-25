package com.example.topnews.ui.di

import com.example.topnews.data.NewsLocalDataSource
import com.example.topnews.data.NewsRemoteDataSource
import com.example.topnews.data.repository.NewsRepository
import com.example.topnews.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        newsLocalDataSource: NewsLocalDataSource,
        newsRemoteDataSource: NewsRemoteDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsLocalDataSource, newsRemoteDataSource)
    }
}