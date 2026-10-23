package com.ejcode.topnews.core.data.di

import com.ejcode.topnews.core.data.news.OfflineFirstNewsRepository
import com.ejcode.topnews.core.data.news.RetrofitNewsDataSource
import com.ejcode.topnews.core.data.news.RoomNewsDataSource
import com.ejcode.topnews.core.data.news.remote.NewsApi
import com.ejcode.topnews.core.data.saved.RoomSavedArticleDataSource
import com.ejcode.topnews.core.domain.news.NewsLocalDataSource
import com.ejcode.topnews.core.domain.news.NewsRemoteDataSource
import com.ejcode.topnews.core.domain.news.NewsRepository
import com.ejcode.topnews.core.domain.saved.SavedArticleLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsDataModule {

    @Binds
    @Singleton
    abstract fun bindNewsRemoteDataSource(impl: RetrofitNewsDataSource): NewsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindNewsLocalDataSource(impl: RoomNewsDataSource): NewsLocalDataSource

    @Binds
    @Singleton
    abstract fun bindSavedArticleLocalDataSource(
        impl: RoomSavedArticleDataSource
    ): SavedArticleLocalDataSource

    @Binds
    @Singleton
    abstract fun bindNewsRepository(impl: OfflineFirstNewsRepository): NewsRepository

    companion object {
        @Provides
        @Singleton
        fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)
    }
}
