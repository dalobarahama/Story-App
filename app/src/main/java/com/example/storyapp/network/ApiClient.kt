package com.example.storyapp.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val BASE_URL = "https://story-api.dicoding.dev/v1/"
    val MAP_URL = "https://maps.googleapis.com/maps/api/"

    fun getApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        Log.d("ApiClient", "getApiService: called")
        return retrofit.create(ApiService::class.java)
    }

    fun getMapsApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(MAP_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        Log.d("ApiClient", "getApiService: called")
        return retrofit.create(ApiService::class.java)
    }
}