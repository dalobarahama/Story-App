package com.example.storyapp.view.login

import com.example.storyapp.view.common.BaseViewMvc

interface LoginViewMvc : BaseViewMvc<LoginViewMvc.Listener> {
    interface Listener {
        fun onClickRegister()
        fun onClickLogin(email: String, password: String)
    }
    
    fun showToast(message: String)
    fun showProgressBar()
    fun hideProgressBar()
}