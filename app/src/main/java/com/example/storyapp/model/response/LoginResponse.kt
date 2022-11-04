package com.example.storyapp.model.response

import com.example.storyapp.model.UserModel

data class LoginResponse(
    var error: Boolean,
    var message: String,
    var loginResult: UserModel
)
