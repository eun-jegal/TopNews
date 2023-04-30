package com.example.topnews.di

import com.example.topnews.data.repository.NewsRepository
import com.example.topnews.ui.viewmodel.NewsViewModelFactory
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
    fun provideViewModelFactory(newsRepository: NewsRepository): NewsViewModelFactory {
        return NewsViewModelFactory(newsRepository)
    }
}