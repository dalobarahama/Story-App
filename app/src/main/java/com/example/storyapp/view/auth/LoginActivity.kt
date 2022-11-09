package com.example.storyapp.view.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.customview.MyEditText
import com.example.storyapp.network.RestApiService
import com.example.storyapp.view.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var loginButton: Button
    private lateinit var register: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: MyEditText
    private lateinit var image: ImageView
    private lateinit var tvLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = findViewById(R.id.progress_circular_login)

        loginButton = findViewById(R.id.btn_login)
        register = findViewById(R.id.tv_register)
        etEmail = findViewById(R.id.et_login_email)
        etPassword = findViewById(R.id.et_login_password)
        image = findViewById(R.id.iv_login_logo)
        tvLogin = findViewById(R.id.tv_login)

        loginButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            login(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }
        register.setOnClickListener { goToRegister() }

        playAnimation()
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

    private fun playAnimation() {
        ObjectAnimator.ofFloat(image, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(tvLogin, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(etEmail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(etPassword, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(register, View.ALPHA, 1f).setDuration(500)
        val button = ObjectAnimator.ofFloat(loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, email, password, register, button)
            start()
        }
    }
}