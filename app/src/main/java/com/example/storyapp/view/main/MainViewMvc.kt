package com.example.storyapp.view.main

import androidx.fragment.app.Fragment
import com.example.storyapp.view.common.BaseViewMvc

interface MainViewMvc : BaseViewMvc<MainViewMvc.Listener> {
    interface Listener {
        fun loadFragment(fragment: Fragment, container: Int)
        fun onPositiveDialogPressed()
    }

    fun showAlertDialog()
    fun setDefaultFragment()
}