package com.example.topnews.ui.viewmodel

import com.example.topnews.data.repository.FakeNewsRepository
import org.junit.Before
import org.junit.Test

class NewsViewModelTest {

    private lateinit var viewModel: NewsViewModel

    @Before
    fun setUp() {
        viewModel = NewsViewModel(FakeNewsRepository())
    }

    @Test
    fun getTopHeadlines() {
    }

    @Test
    fun getHeadlinesByCategory() {
    }

    @Test
    fun getSearchedHeadlines() {
    }

    @Test
    fun testGetTopHeadlines() {
    }

    @Test
    fun testGetHeadlinesByCategory() {
    }

    @Test
    fun testGetSearchedHeadlines() {
    }

    @Test
    fun `insert article item with empty field, returns error`() {

    }

    @Test
    fun getSavedArticles() {
    }

    @Test
    fun deleteArticle() {
    }

    @Test
    fun deleteAllArticles() {
    }
}