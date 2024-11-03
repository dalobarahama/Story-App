package com.example.storyapp.view.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.storyapp.view.login.LoginViewMvc
import com.example.storyapp.view.login.LoginViewMvcImpl
import com.example.storyapp.view.main.MainViewMvc
import com.example.storyapp.view.main.MainViewMvcImpl
import com.example.storyapp.view.register.RegisterViewMvc
import com.example.storyapp.view.register.RegisterViewMvcImpl

class ViewMvcFactory(private val layoutInflater: LayoutInflater) {

    fun getLoginViewMvc(parent: ViewGroup?): LoginViewMvc {
        return LoginViewMvcImpl(layoutInflater, parent)
    }

    fun getRegisterViewMvc(parent: ViewGroup?): RegisterViewMvc {
        return RegisterViewMvcImpl(layoutInflater, parent)
    }

    fun getMainViewMvc(parent: ViewGroup?): MainViewMvc {
        return MainViewMvcImpl(layoutInflater, parent)
    }
}