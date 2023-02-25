package com.example.topnews.ui.di

import com.example.topnews.data.NewsLocalDataSource
import com.example.topnews.data.NewsLocalDataSourceImpl
import com.example.topnews.data.db.ArticleDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun providesNewsLocalDataSource(articleDAO: ArticleDAO): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDAO)
    }
}