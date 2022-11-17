package com.example.storyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.repository.StoryAppRepository

class StoryViewModel constructor(private val storyAppRepository: StoryAppRepository) : ViewModel() {


    fun getStory(): LiveData<PagingData<StoryModel>> =
        storyAppRepository.getStory().cachedIn(viewModelScope)

    fun getStoryWithLocation(): LiveData<List<StoryModel>> =
        storyAppRepository.getStoryWithLocation()
}