package com.example.topnews.ui.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.topnews.data.db.ArticleDAO
import com.example.topnews.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideArticleDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(
            app,
            ArticleDatabase::class.java,
            "topnews_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideArticleDAO(articleDatabase: ArticleDatabase): ArticleDAO {
        return articleDatabase.getArticleDAO()
    }
}