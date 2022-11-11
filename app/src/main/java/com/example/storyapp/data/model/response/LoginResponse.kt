package com.example.storyapp.data.model.response

import com.example.storyapp.data.model.UserModel

data class LoginResponse(
    var error: Boolean,
    var message: String,
    var loginResult: UserModel
)
