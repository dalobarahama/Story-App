package com.example.storyapp.model

data class LoginResponse(
    var error: Boolean,
    var message: String,
    var loginResult: UserModel
)
