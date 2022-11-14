package com.example.storyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.repository.StoryAppRepository
import com.example.storyapp.utils.DataDummy
import com.example.storyapp.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: StoryAppRepository
    private lateinit var storyViewModel: StoryViewModel
    private val dummyStory = DataDummy.generatePagingDataDummy()

    @Before
    fun setUp() {
        storyViewModel = StoryViewModel(repository)
    }

    @Test
    fun `when Get StoryList should not null`() {
        val observer = Observer<PagingData<StoryModel>> {}
        try {
            val expectedStory = MutableLiveData<PagingData<StoryModel>>()
            expectedStory.value = dummyStory
            `when`(repository.getStory("token")).thenReturn(expectedStory)
            val actualData = storyViewModel.getStory("token").observeForever(observer)
            Assert.assertNotNull(actualData)
        } finally {
            storyViewModel.getStory("token").removeObserver(observer)
        }

    }
}