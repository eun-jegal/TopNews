package com.example.topnews.ui.di

import com.example.topnews.data.repository.NewsRepository
import com.example.topnews.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetTopHeadlinesUseCase(newsRepository: NewsRepository): GetTopHeadlinesUseCase {
        return GetTopHeadlinesUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetHeadlinesByCategoryUseCase(newsRepository: NewsRepository): GetHeadlinesByCategoryUseCase {
        return GetHeadlinesByCategoryUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchedHeadlinesUseCase(newsRepository: NewsRepository): GetSearchedHeadlinesUseCase {
        return GetSearchedHeadlinesUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSaveArticleUseCase(newsRepository: NewsRepository): SaveArticleUseCase {
        return SaveArticleUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteArticleUseCase(newsRepository: NewsRepository): DeleteArticleUseCase {
        return DeleteArticleUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllArticlesUseCase(newsRepository: NewsRepository): GetAllArticlesUseCase {
        return GetAllArticlesUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteAllArticlesUseCase(newsRepository: NewsRepository): DeleteAllArticlesUseCase {
        return DeleteAllArticlesUseCase(newsRepository)
    }
}