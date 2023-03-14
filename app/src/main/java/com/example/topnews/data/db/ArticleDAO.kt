package com.example.topnews.data.db

import androidx.room.*
import com.example.topnews.data.model.Article

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<Article>

    @Query("DELETE FROM articles")
    fun deleteAllArticles()
}