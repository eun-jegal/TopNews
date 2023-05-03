package com.example.topnews.data.repository

import com.example.topnews.testArticle
import com.example.topnews.testArticle2
import com.example.topnews.testArticle3
import com.example.topnews.testArticleList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryImplTest {

    private val testLocalDataSource = FakeNewsLocalDataSource()
    private val testRemoteDataSource = FakeNewsRemoteDataSource()
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        newsRepository = NewsRepositoryImpl(
            newsLocalDataSource = testLocalDataSource,
            newsRemoteDataSource = testRemoteDataSource
        )
    }

    @Test
    fun getTopHeadlines() = runTest {
        val headlines = newsRepository.getTopHeadlines("us", 0).data?.articles
        headlines?.forEachIndexed { index, article ->
            assertTrue(article.title == testArticleList[index].title)
        }
    }

    @Test
    fun getHeadlinesByCategory() = runTest {
        val validCategory = "science"
        newsRepository.getHeadlinesByCategory(
            "us",
            0,
            validCategory
        ).data?.articles?.forEachIndexed { index, article ->
            assertTrue(article.title == testArticleList[index].title)
        }

        val invalidCategory = "crypto"
        assertTrue(
            newsRepository.getHeadlinesByCategory(
                "us",
                0,
                invalidCategory
            ).message!!.isNotEmpty()
        )
    }

    @Test
    fun getSearchedTopHeadlines() = runTest {
        val validSearchQuery = "sports"
        newsRepository.getSearchedTopHeadlines(
            "us",
            validSearchQuery,
            0
        ).data?.articles?.forEachIndexed { index, article ->
            assertTrue(article.title == testArticleList[index].title)
        }

        val invalidSearchQuery = ""
        assertTrue(
            newsRepository.getSearchedTopHeadlines(
                "us",
                invalidSearchQuery,
                0
            ).message!!.isNotEmpty()
        )
    }

    @Test
    fun test_local_data_source_operations() = runTest {
        newsRepository.saveArticle(testArticle)
        newsRepository.deleteArticle(testArticle)
        newsRepository.saveArticle(testArticle2)
        newsRepository.saveArticle(testArticle3)
        assertTrue(newsRepository.getAllArticles().isNotEmpty())

        newsRepository.deleteAllArticles()
        assertTrue(newsRepository.getAllArticles().isEmpty())
    }
}