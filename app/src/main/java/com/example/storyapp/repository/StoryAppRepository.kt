package com.example.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.data.StoryPagingSource
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.data.model.response.StoryResponse
import com.example.storyapp.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun getStoryWithLocation(token: String): LiveData<List<StoryModel>> {
        val storiesWithLocationLiveData = MutableLiveData<List<StoryModel>>()
        apiService.getStoriesWithLocation(token).enqueue(
            object : Callback<StoryResponse> {
                override fun onResponse(
                    call: Call<StoryResponse>,
                    response: Response<StoryResponse>,
                ) {
                    storiesWithLocationLiveData.value = response.body()!!.listStory
                }

                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                    Log.d("StoryAppRepository", "onFailure: ${t.message}")
                }

            }
        )
        return storiesWithLocationLiveData
    }
}