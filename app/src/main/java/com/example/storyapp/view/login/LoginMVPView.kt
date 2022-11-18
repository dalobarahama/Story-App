package com.example.storyapp.view.login

interface LoginMVPView {
    fun showToast(message: String)
    fun showProgressBar()
    fun hideProgressBar()
    fun goToRegisterActivity()
    fun goToMainActivity()
}