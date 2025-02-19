package com.example.storyapp.view.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.storyapp.R
import com.example.storyapp.view.common.BaseViewMvcImpl

class ProfileViewMvcImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) : ProfileViewMvc, BaseViewMvcImpl<ProfileViewMvc.Listener>() {

    private val tvUsername: TextView

    init {
        setRootView(layoutInflater.inflate(R.layout.activity_profile, parent, false))

        tvUsername = findViewById(R.id.tv_profile_username)
        val btnLogout = findViewById<Button>(R.id.btn_profile_logout)

        btnLogout.setOnClickListener {
            getListener()?.onBtnLogoutPressed()
        }
    }

    override fun bindView(username: String) {
        tvUsername.text = username
    }

}