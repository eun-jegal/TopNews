package com.example.topnews.domain

import com.example.topnews.data.repository.NewsRepository
import com.example.topnews.data.util.NetworkResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetHeadlinesByCategoryUseCaseTest {

    private lateinit var repository: NewsRepository
    private lateinit var useCase: GetHeadlinesByCategoryUseCase

    @Before
    fun setUp() {
        repository = FakeRepository()
        useCase = GetHeadlinesByCategoryUseCase(repository)
    }

    @Test
    fun test_use_case_execution() = runBlocking {
        val success = useCase.execute("us", 1, "General")
        assertTrue(success is NetworkResult.Success)
        val error = useCase.execute("mn", 1, "Wrong Category")
        assertTrue(error is NetworkResult.Error)
    }
}