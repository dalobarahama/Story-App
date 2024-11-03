package com.example.storyapp.view.register

import com.example.storyapp.view.common.BaseViewMvc

interface RegisterViewMvc : BaseViewMvc<RegisterViewMvc.Listener> {
    interface Listener {
        fun onRegisterButtonClicked(name: String, email: String, password: String)
    }

    fun showToast(message: String)
    fun showProgressBar()
    fun hideProgressBar()
}