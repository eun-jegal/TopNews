package com.example.topnews.domain

import com.example.topnews.data.repository.NewsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify

class DeleteAllArticlesUseCaseTest {

    private lateinit var repository: NewsRepository
    private lateinit var useCase: DeleteAllArticlesUseCase

    @Before
    fun setUp() {
        repository = Mockito.mock(NewsRepository::class.java)
        useCase = DeleteAllArticlesUseCase(repository)
    }

    @Test
    fun test_use_case_execution() = runBlocking {
        useCase.execute()
        verify(repository, atLeastOnce()).deleteAllArticles()
    }
}