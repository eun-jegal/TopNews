package com.example.topnews.data.repository

import com.example.topnews.data.NewsLocalDataSource
import com.example.topnews.data.NewsRemoteDataSource
import com.example.topnews.data.util.NetworkResult
import com.example.topnews.domain.TestUtil
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify

class NewsRepositoryImplTest {

    private lateinit var localDataSource: NewsLocalDataSource
    private lateinit var remoteDataSource: NewsRemoteDataSource
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        localDataSource = Mockito.mock(NewsLocalDataSource::class.java)
        remoteDataSource = FakeRemoteDataSource()
        newsRepository = NewsRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun getTopHeadlines() = runBlocking {
        val success = newsRepository.getTopHeadlines("us", 1)
        assertTrue(success is NetworkResult.Success)
        val error = newsRepository.getTopHeadlines("vk", 1)
        assertTrue(error is NetworkResult.Error)
    }

    @Test
    fun getHeadlinesByCategory() = runBlocking {
        val success = newsRepository.getHeadlinesByCategory("us", 1, "General")
        assertTrue(success is NetworkResult.Success)
        val error = newsRepository.getHeadlinesByCategory("us", 1, "Wrong value")
        assertTrue(error is NetworkResult.Error)
    }

    @Test
    fun getSearchedTopHeadlines() = runBlocking {
//        val success = newsRepository.getSearchedTopHeadlines("us", "sport", 1)
//        assertTrue(success is NetworkResult.Success)
//        val error = newsRepository.getSearchedTopHeadlines("us", "Wrong value", 1)
//        assertTrue(error is NetworkResult.Error)
    }

    @Test
    fun saveArticle() = runBlocking {
        val article = TestUtil.testArticle
        newsRepository.saveArticle(article)
        verify(localDataSource, atLeastOnce()).saveArticle(article)
    }

    @Test
    fun deleteArticle() = runBlocking {
        val article = TestUtil.testArticle
        newsRepository.deleteArticle(article)
        verify(localDataSource, atLeastOnce()).deleteArticle(article)
    }

    @Test
    fun getAllArticles() {
//        Mockito.`when`(localDataSource.getAllArticles()).thenReturn(flow { emit(TestUtil.testArticleList) })
//        val articlesFromDB = newsRepository.getAllArticles().collec
//        assertTrue(articlesFromDB.size == TestUtil.testArticleList.size)
    }

    @Test
    fun deleteAllArticles() {
        newsRepository.deleteAllArticles()
        verify(localDataSource, atLeastOnce()).deleteAllArticles()
    }
}