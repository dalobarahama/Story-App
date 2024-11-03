package com.example.storyapp.view.login

import android.view.View
import com.example.storyapp.view.login.LoginMvpImpl.Listener

interface LoginMvc {
    fun getRootView(): View
    fun <T : View?> findViewById(id: Int): T
    fun registerListener(listener: Listener)
    fun unregisterListener(listener: Listener)
    fun showToast(message: String)
    fun showProgressBar()
    fun hideProgressBar()
}