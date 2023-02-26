package com.example.topnews.ui.di

import com.example.topnews.domain.*
import com.example.topnews.ui.viewmodel.NewsViewModelFactory
import com.google.gson.annotations.SerializedName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
        getHeadlinesByCategoryUseCase: GetHeadlinesByCategoryUseCase,
        getSearchedHeadlinesUseCase: GetSearchedHeadlinesUseCase,
        saveArticleUseCase: SaveArticleUseCase,
        deleteArticleUseCase: DeleteArticleUseCase,
        getAllArticlesUseCase: GetAllArticlesUseCase,
        deleteAllArticlesUseCase: DeleteAllArticlesUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
            getTopHeadlinesUseCase,
            getHeadlinesByCategoryUseCase,
            getSearchedHeadlinesUseCase,
            saveArticleUseCase,
            deleteArticleUseCase,
            getAllArticlesUseCase,
            deleteAllArticlesUseCase
        )
    }
}