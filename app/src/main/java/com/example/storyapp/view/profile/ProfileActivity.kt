package com.example.storyapp.view.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.view.auth.LoginActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "username") ?: ""

        val textViewUsername = findViewById<TextView>(R.id.tv_profile_username)
        textViewUsername.text = username

        val buttonLogout = findViewById<Button>(R.id.btn_profile_logout)
        buttonLogout.setOnClickListener {
            sharedPref.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}