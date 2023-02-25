package com.example.topnews.ui.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.topnews.data.db.ArticleDAO
import com.example.topnews.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideArticleDatabase(context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context,
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