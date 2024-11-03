package com.example.storyapp.view.register

import android.view.View
import com.example.storyapp.view.register.RegisterMvcImpl.Listener

interface RegisterMvc {
    fun getRootView(): View
    fun <T : View?> findViewById(id: Int): T
    fun registerListener(listener: Listener)
    fun unregisterListener(listener: Listener)
    fun showToast(message: String)
    fun showProgressBar()
    fun hideProgressBar()
}