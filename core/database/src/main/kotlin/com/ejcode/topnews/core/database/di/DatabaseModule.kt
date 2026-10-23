package com.ejcode.topnews.core.database.di

import android.content.Context
import androidx.room.Room
import com.ejcode.topnews.core.database.TopNewsDatabase
import com.ejcode.topnews.core.database.dao.HeadlineDao
import com.ejcode.topnews.core.database.dao.SavedArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TopNewsDatabase =
        Room.databaseBuilder(context, TopNewsDatabase::class.java, "topnews.db").build()

    @Provides
    @Singleton
    fun provideHeadlineDao(database: TopNewsDatabase): HeadlineDao = database.headlineDao()

    @Provides
    @Singleton
    fun provideSavedArticleDao(database: TopNewsDatabase): SavedArticleDao =
        database.savedArticleDao()
}
