package com.example.topnews.di

import android.content.Context
import androidx.room.Room
import com.example.topnews.data.db.ArticleDAO
import com.example.topnews.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideArticleDatabase(
        @ApplicationContext context: Context
    ): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            "topnews_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideArticleDAO(
        database: ArticleDatabase
    ): ArticleDAO {
        return database.getArticleDAO()
    }
}