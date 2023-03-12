package com.example.topnews.domain

import com.example.topnews.data.repository.NewsRepository
import com.example.topnews.data.util.NetworkResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetTopHeadlinesUseCaseTest {

    private lateinit var repository: NewsRepository
    private lateinit var useCase: GetTopHeadlinesUseCase

    @Before
    fun setUp() {
        repository = FakeRepository()
        useCase = GetTopHeadlinesUseCase(repository)
    }

    @Test
    fun test_use_case_execution() = runBlocking {
        val success = useCase.execute("us", 1)
        assertTrue(success is NetworkResult.Success)
        val error = useCase.execute("mn", 1)
        assertTrue(error is NetworkResult.Error)
    }
}