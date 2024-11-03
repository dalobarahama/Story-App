package com.example.storyapp.view.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.storyapp.view.login.LoginViewMvc
import com.example.storyapp.view.login.LoginViewMvcImpl

class ViewMvcFactory(private val layoutInflater: LayoutInflater) {

    fun getLoginViewMvc(parent: ViewGroup?): LoginViewMvc {
        return LoginViewMvcImpl(layoutInflater, parent)
    }
}