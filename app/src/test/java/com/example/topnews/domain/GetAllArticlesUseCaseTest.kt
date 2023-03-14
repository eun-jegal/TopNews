package com.example.topnews.domain

import com.example.topnews.data.repository.NewsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class GetAllArticlesUseCaseTest {

    private lateinit var repository: NewsRepository
    private lateinit var useCase: GetAllArticlesUseCase

    @Before
    fun setUp() {
        repository = FakeRepository()
        useCase = GetAllArticlesUseCase(repository)
    }

    @Test
    fun test_use_case_execution() = runBlocking {
        val articles = useCase.execute()
        assertTrue(articles.size == TestUtil.testArticleList.size)
    }
}