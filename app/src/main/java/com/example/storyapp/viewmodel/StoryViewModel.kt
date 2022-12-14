package com.example.storyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.network.RestApiService
import com.example.storyapp.repository.StoryAppRepository

class StoryViewModel constructor(private val storyAppRepository: StoryAppRepository) : ViewModel() {
    private var storiesWithLocationLiveData = MutableLiveData<List<StoryModel>>()

    fun getStory(token: String): LiveData<PagingData<StoryModel>> =
        storyAppRepository.getStory(token).cachedIn(viewModelScope)

    fun getStoryWithLocation(token: String) {
        val apiService = RestApiService()
        apiService.getStoriesWithLocation(token) {
            if (it?.error == false) {
                storiesWithLocationLiveData.value = it.listStory
            }
        }
    }

    fun observeStoryWithLocationLiveData(): LiveData<List<StoryModel>> {
        return storiesWithLocationLiveData
    }
}