package com.example.storyapp.view.login

interface LoginMVPPresenter {
    fun onLoginClicked(email: String, password: String)
    fun goToRegister()
}