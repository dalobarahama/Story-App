package com.example.storyapp.view.common

import android.view.View

abstract class BaseViewMvcImpl<ListenerType> : BaseViewMvc<ListenerType> {
    private lateinit var rootView: View
    private var listeners = HashSet<ListenerType>()

    override fun getRootView(): View = rootView

    override fun <T : View?> findViewById(id: Int): T {
        return getRootView().findViewById<T>(id)
    }

    override fun registerListener(listener: ListenerType) {
        listeners.add(listener)
    }

    override fun unregisterListener(listener: ListenerType) {
        listeners.remove(listener)
    }

    protected fun setRootView(rootView: View) {
        this.rootView = rootView
    }

    protected fun getListener(): ListenerType? {
        var listener: ListenerType? = null
        for (item in listeners) {
            listener = item
        }
        return listener
    }
}