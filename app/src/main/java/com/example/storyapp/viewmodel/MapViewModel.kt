package com.example.storyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.data.model.newdata.NewMapResponse
import com.example.storyapp.data.model.response.MapResponse
import com.example.storyapp.repository.StoryAppRepository

class MapViewModel constructor(private val storyAppRepository: StoryAppRepository) : ViewModel() {
    fun getTextSearch(
        apiKey: String,
        locationName: String,
        radius: String,
        location: String,
    ): LiveData<MapResponse> =
        storyAppRepository.getTextSearch(apiKey, locationName, radius, location)
}