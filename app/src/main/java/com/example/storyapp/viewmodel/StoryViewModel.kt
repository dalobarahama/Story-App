package com.example.storyapp.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.di.Injection
import com.example.storyapp.network.RestApiService
import com.example.storyapp.repository.StoryAppRepository

class StoryViewModel constructor(private val storyAppRepository: StoryAppRepository) : ViewModel() {
    private var storiesLiveData = MutableLiveData<List<StoryModel>>()
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

class ViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryViewModel(Injection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}