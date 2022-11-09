package com.example.storyapp.view.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.network.RestApiService
import com.example.storyapp.view.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = findViewById(R.id.progress_circular_login)

        val loginButton = findViewById<Button>(R.id.btn_login)
        val register = findViewById<TextView>(R.id.tv_register)
        val etEmail = findViewById<EditText>(R.id.et_login_email)
        val etPassword = findViewById<EditText>(R.id.et_login_password)

        loginButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            login(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }
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

    private fun login(email: String, password: String) {
        val apiService = RestApiService()
        apiService.login(email, password) {
            if (it?.message == "success") {
                progressBar.visibility = View.GONE
                goToHome()

                val token = "Bearer ${it.loginResult.token}"
                val sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("token", token)
                    putString("username", it.loginResult.name)
                    apply()
                }

                Log.d("Login", "token: $token")
            } else {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}