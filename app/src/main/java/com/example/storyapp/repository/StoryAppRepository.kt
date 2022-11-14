package com.example.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.data.StoryPagingSource
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.network.ApiService

class StoryAppRepository constructor(private val apiService: ApiService) {

    fun getStory(token: String): LiveData<PagingData<StoryModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).liveData

    }
}