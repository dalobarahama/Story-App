package com.example.storyapp.di

import android.content.Context
import com.example.storyapp.network.ApiClient
import com.example.storyapp.repository.StoryAppRepository

object Injection {
    fun provideRepository(context: Context): StoryAppRepository {
        val apiService = ApiClient.getApiService()
        return StoryAppRepository(context, apiService)
    }
}