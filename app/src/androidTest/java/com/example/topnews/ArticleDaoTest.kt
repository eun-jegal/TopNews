package com.example.topnews

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.topnews.data.db.ArticleDAO
import com.example.topnews.data.db.ArticleDatabase
import com.example.topnews.data.model.Article
import com.example.topnews.data.model.Source
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ArticleDaoTest {

    private lateinit var database: ArticleDatabase
    private lateinit var dao: ArticleDAO

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getArticleDAO()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveArticle() = runBlocking {
         val article = Article(
             id = 1,
             source = Source(id = "cnn", name = "CNN"),
             author = "Nathaniel Meyersohn",
             description = "A toy company based in New York has gotten caught up in the collapse of Silicon Valley Bank and is pleading with customers for help keeping it afloat.",
             title = "Camp toy store pleads for help after Silicon Valley Bank collapse - CNN",
             url = "https://www.cnn.com/2023/03/10/business/camp-toy-store-svb-bank/index.html",
             urlToImage = "https://media.cnn.com/api/v1/images/stellar/prod/230310161851-camp-store-file-restricted.jpg?c=16x9&q=w_800,c_fill",
             publishedAt = "2023-03-11T16:21:00Z",
             content = "A toy company based in New York has gotten caught up in the collapse of Silicon Valley Bank and is pleading with customers for help keeping it afloat.\r\nCamp, a venture-backed retailer, sent an email … [+1685 chars]",
         )
        dao.save(article)
        val articles = dao.getAllArticles()

        assertTrue(article.title == articles[0].title)
    }

    @Test
    fun deleteArticle() = runBlocking {
        val article = Article(
            id = 1,
            source = Source(id = "cnn", name = "CNN"),
            author = "Nathaniel Meyersohn",
            description = "A toy company based in New York has gotten caught up in the collapse of Silicon Valley Bank and is pleading with customers for help keeping it afloat.",
            title = "Camp toy store pleads for help after Silicon Valley Bank collapse - CNN",
            url = "https://www.cnn.com/2023/03/10/business/camp-toy-store-svb-bank/index.html",
            urlToImage = "https://media.cnn.com/api/v1/images/stellar/prod/230310161851-camp-store-file-restricted.jpg?c=16x9&q=w_800,c_fill",
            publishedAt = "2023-03-11T16:21:00Z",
            content = "A toy company based in New York has gotten caught up in the collapse of Silicon Valley Bank and is pleading with customers for help keeping it afloat.\r\nCamp, a venture-backed retailer, sent an email … [+1685 chars]",
        )
        dao.save(article)
        dao.delete(article)

        assertTrue(dao.getAllArticles().isEmpty())
    }

}