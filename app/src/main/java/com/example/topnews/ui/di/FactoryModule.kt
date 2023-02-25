package com.example.topnews.ui.di

import android.app.Application
import com.example.topnews.domain.*
import com.example.topnews.ui.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FactoryModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        app: Application,
        getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
        getHeadlinesByCategoryUseCase: GetHeadlinesByCategoryUseCase,
        getSearchedHeadlinesUseCase: GetSearchedHeadlinesUseCase,
        saveArticleUseCase: SaveArticleUseCase,
        deleteArticleUseCase: DeleteArticleUseCase,
        getAllArticlesUseCase: GetAllArticlesUseCase,
        deleteAllArticlesUseCase: DeleteAllArticlesUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
            app,
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