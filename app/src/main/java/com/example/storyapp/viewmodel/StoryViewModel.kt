package com.example.storyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.network.RestApiService

class StoryViewModel : ViewModel() {
    private var storiesLiveData = MutableLiveData<List<StoryModel>>()
    private var storiesWithLocationLiveData = MutableLiveData<List<StoryModel>>()

    fun getStory(token: String) {
        val apiService = RestApiService()
        apiService.getStories(token) {
            if (it?.error == false) {
                storiesLiveData.value = it.listStory
            }
        }
    }

    fun observeStoryLiveData(): LiveData<List<StoryModel>> {
        return storiesLiveData
    }

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