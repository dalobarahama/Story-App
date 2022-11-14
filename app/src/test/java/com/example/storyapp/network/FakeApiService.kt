package com.example.storyapp.network

import com.example.storyapp.data.model.response.CommonResponse
import com.example.storyapp.data.model.response.DetailStoryResponse
import com.example.storyapp.data.model.response.LoginResponse
import com.example.storyapp.data.model.response.StoryResponse
import com.example.storyapp.utils.DataDummy
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class FakeApiService : ApiService {
    private val getStoriesDummy = DataDummy.getStoriesDummy()

    override fun login(email: String, password: String): Call<LoginResponse> {
        TODO("Not yet implemented")
    }

    override fun register(name: String, email: String, password: String): Call<CommonResponse> {
        TODO("Not yet implemented")
    }

    override fun uploadStory(
        token: String,
        description: RequestBody,
        image: MultipartBody.Part,
        lat: RequestBody?,
        lon: RequestBody?,
    ): Call<CommonResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getStories(token: String, page: Int, size: Int): Response<StoryResponse> {
        return getStoriesDummy
    }

    override fun getStoriesWithLocation(token: String): Call<StoryResponse> {
        TODO("Not yet implemented")
    }

    override fun getDetailStory(token: String, idStory: String): Call<DetailStoryResponse> {
        TODO("Not yet implemented")
    }

}