package com.example.topnews.domain

import com.example.topnews.data.repository.NewsRepository
import com.example.topnews.data.util.NetworkResult
import io.mockk.every
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
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