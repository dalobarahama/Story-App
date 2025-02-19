package com.example.storyapp.view.profile

import com.example.storyapp.view.common.BaseViewMvc

interface ProfileViewMvc: BaseViewMvc<ProfileViewMvc.Listener> {

    interface Listener {
        fun onBtnLogoutPressed()
    }

    fun bindView(username: String)
}