package com.example.storyapp.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.view.MainActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.btn_login)
        val register = findViewById<TextView>(R.id.tv_register)

        loginButton.setOnClickListener { goToHome() }
        register.setOnClickListener { goToRegister() }
    }

    private fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun goToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}