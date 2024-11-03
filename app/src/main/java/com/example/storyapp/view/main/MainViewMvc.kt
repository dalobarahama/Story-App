package com.example.storyapp.view.main

import android.view.View
import com.example.storyapp.view.main.MainViewMvcImpl.Listener

interface MainViewMvc {
    fun getRootView(): View
    fun <T : View?> findViewById(id: Int): T
    fun registerListener(listener: Listener)
    fun unregisterListener(listener: Listener)
    fun showAlertDialog()
    fun setDefaultFragment()
}