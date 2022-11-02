package com.example.storyapp.network

import com.example.storyapp.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
    fun login(email: String, password: String, onResult: (UserResponse?) -> Unit) {
        val retrofit = ApiClient.getApiService()
        retrofit.login(email, password).enqueue(
            object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    onResult(null)
                }

            }
        )
    }
}