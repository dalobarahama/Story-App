package com.example.storyapp.view.common

import android.view.View

interface BaseViewMvc<ListenerType> {
    fun getRootView(): View
    fun <T : View?> findViewById(id: Int): T
    fun registerListener(listener: ListenerType)
    fun unregisterListener(listener: ListenerType)
}