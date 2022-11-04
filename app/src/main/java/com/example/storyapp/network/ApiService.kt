package com.example.storyapp.network

import com.example.storyapp.model.response.DetailStoryResponse
import com.example.storyapp.model.response.LoginResponse
import com.example.storyapp.model.response.RegisterResponse
import com.example.storyapp.model.response.StoryResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("stories")
    fun getStories(@Header("Authorization") token: String): Call<StoryResponse>

    @GET("stories/{id}")
    fun getDetailStory(
        @Header("Authorization") token: String,
        @Path("id") idStory: String
    ): Call<DetailStoryResponse>
}