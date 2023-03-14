package com.example.topnews.domain

import com.example.topnews.data.repository.NewsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify

class SaveArticleUseCaseTest {

    private lateinit var repository: NewsRepository
    private lateinit var useCase: SaveArticleUseCase

    @Before
    fun setUp() {
        repository = Mockito.mock(NewsRepository::class.java)
        useCase = SaveArticleUseCase(repository)
    }

    @Test
    fun test_use_case_execution() = runBlocking {
        val article = TestUtil.testArticle
        useCase.execute(article)
        verify(repository, atLeastOnce()).saveArticle(article)
    }
}