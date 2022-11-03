package com.example.storyapp.network

import com.example.storyapp.model.DetailStoryResponse
import com.example.storyapp.model.LoginResponse
import com.example.storyapp.model.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
    private val retrofit = ApiClient.getApiService()

    fun login(email: String, password: String, onResult: (LoginResponse?) -> Unit) {
        retrofit.login(email, password).enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }

    fun getStories(token: String, onResult: (StoryResponse?) -> Unit) {
        retrofit.getStories(token).enqueue(
            object : Callback<StoryResponse> {
                override fun onResponse(
                    call: Call<StoryResponse>,
                    response: Response<StoryResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }

    fun getDetailStory(token: String, idStory: String, onResult: (DetailStoryResponse?) -> Unit) {
        retrofit.getDetailStory(token, idStory).enqueue(
            object : Callback<DetailStoryResponse> {
                override fun onResponse(
                    call: Call<DetailStoryResponse>,
                    response: Response<DetailStoryResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }
}