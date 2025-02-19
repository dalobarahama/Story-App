package com.example.storyapp.view.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.view.common.ViewMvcFactory
import com.example.storyapp.view.login.LoginActivity

class ProfileActivity : AppCompatActivity(), ProfileViewMvc.Listener {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var viewMvc: ProfileViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewMvcFactory = ViewMvcFactory(layoutInflater)
        viewMvc = viewMvcFactory.getProfileViewMvc(null)
        setContentView(viewMvc.getRootView())

        sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "username") ?: ""

        viewMvc.bindView(username)
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
    }

    override fun onStop() {
        viewMvc.unregisterListener(this)
        super.onStop()
    }

    override fun onBtnLogoutPressed() {
        sharedPref.edit().clear().apply()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}