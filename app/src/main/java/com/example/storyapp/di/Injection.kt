package com.example.storyapp.di

import com.example.storyapp.network.ApiClient
import com.example.storyapp.repository.StoryAppRepository

object Injection {
    fun provideRepository(): StoryAppRepository {
        val apiService = ApiClient.getApiService()
        return StoryAppRepository(apiService)
    }

    fun provideMapRepository(): StoryAppRepository {
        val apiService = ApiClient.getMapsApiService()
        return StoryAppRepository(apiService)
    }
}