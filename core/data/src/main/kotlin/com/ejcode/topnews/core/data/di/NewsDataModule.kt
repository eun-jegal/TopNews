package com.ejcode.topnews.core.data.di

import com.ejcode.topnews.core.data.news.RetrofitNewsDataSource
import com.ejcode.topnews.core.data.news.remote.NewsApi
import com.ejcode.topnews.core.domain.news.NewsRemoteDataSource
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

    companion object {
        @Provides
        @Singleton
        fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)
    }
}
