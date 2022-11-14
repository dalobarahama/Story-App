package com.example.storyapp.network

import android.util.Log
import com.example.storyapp.data.model.response.CommonResponse
import com.example.storyapp.data.model.response.DetailStoryResponse
import com.example.storyapp.data.model.response.LoginResponse
import com.example.storyapp.data.model.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
    private val retrofit = ApiClient.getApiService()

    fun register(
        name: String,
        email: String,
        password: String,
        onResult: (CommonResponse?) -> Unit,
    ) {
        retrofit.register(name, email, password).enqueue(
            object : Callback<CommonResponse> {
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>,
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }

    fun login(email: String, password: String, onResult: (LoginResponse?) -> Unit) {
        retrofit.login(email, password).enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>,
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }

    fun uploadStory(
        token: String,
        description: RequestBody,
        image: MultipartBody.Part,
        lat: RequestBody?,
        lon: RequestBody?,
        onResult: (CommonResponse?) -> Unit,
    ) {
        retrofit.uploadStory(token, description, image, lat, lon)
            .enqueue(object : Callback<CommonResponse> {
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>,
                ) {
                    Log.d("RestApiService", "onResponse: called")
                    Log.d("RestApiService", "onResponse: $response")
                    Log.d("RestApiService", "onResponse: ${response.body()}")
                    onResult(response.body())
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    onResult(null)
                    Log.d("RestApiService", "onFailure: ${t.message}")
                }

            })
    }

//    fun getStoriesWithLocation(token: String, onResult: (StoryResponse?) -> Unit) {
//        retrofit.getStoriesWithLocation(token).enqueue(
//            object : Callback<StoryResponse> {
//                override fun onResponse(
//                    call: Call<StoryResponse>,
//                    response: Response<StoryResponse>,
//                ) {
//                    onResult(response.body())
//                }
//
//                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                    onResult(null)
//                }
//
//            }
//        )
//    }

    fun getDetailStory(token: String, idStory: String, onResult: (DetailStoryResponse?) -> Unit) {
        retrofit.getDetailStory(token, idStory).enqueue(
            object : Callback<DetailStoryResponse> {
                override fun onResponse(
                    call: Call<DetailStoryResponse>,
                    response: Response<DetailStoryResponse>,
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