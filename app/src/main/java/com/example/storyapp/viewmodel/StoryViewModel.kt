package com.example.storyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.data.model.response.MapResponse
import com.example.storyapp.repository.StoryAppRepository
import com.google.android.gms.maps.model.LatLng

class StoryViewModel constructor(private val storyAppRepository: StoryAppRepository) : ViewModel() {
//    private var storiesWithLocationLiveData = MutableLiveData<List<StoryModel>>()

    fun getStory(token: String): LiveData<PagingData<StoryModel>> =
        storyAppRepository.getStory(token).cachedIn(viewModelScope)

//    fun getStoryWithLocation(token: String) {
//        val apiService = RestApiService()
//        apiService.getStoriesWithLocation(token) {
//            if (it?.error == false) {
//                storiesWithLocationLiveData.value = it.listStory
//            }
//        }
//    }

    fun getStoryWithLocation(token: String): LiveData<List<StoryModel>> =
        storyAppRepository.getStoryWithLocation(token)

}