package com.example.topnews.di

import com.example.topnews.data.NewsLocalDataSource
import com.example.topnews.data.NewsLocalDataSourceImpl
import com.example.topnews.data.db.ArticleDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun providesNewsLocalDataSource(articleDAO: ArticleDAO): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDAO)
    }
}