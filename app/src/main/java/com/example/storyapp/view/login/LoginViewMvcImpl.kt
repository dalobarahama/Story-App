package com.example.storyapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.storyapp.R
import com.example.storyapp.view.common.BaseViewMvcImpl

class LoginViewMvcImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) : LoginViewMvc,
    BaseViewMvcImpl<LoginViewMvc.Listener>() {

    private var progressBar: ProgressBar
    private var loginButton: Button
    private var register: TextView
    private var etEmail: EditText
    private var etPassword: EditText
    private var image: ImageView
    private var tvLogin: TextView

    init {
        setRootView(layoutInflater.inflate(R.layout.activity_login, parent, false))

        progressBar = findViewById(R.id.progress_circular_login)
        loginButton = findViewById(R.id.btn_login)
        register = findViewById(R.id.tv_register)
        etEmail = findViewById(R.id.et_login_email)
        etPassword = findViewById(R.id.et_login_password)
        image = findViewById(R.id.iv_login_logo)
        tvLogin = findViewById(R.id.tv_login)

        loginButton.setOnClickListener {
            getListener()?.onClickLogin(etEmail.text.toString(), etPassword.text.toString())
        }

        register.setOnClickListener {
            getListener()?.onClickRegister()
        }

        playAnimation()
    }

    override fun showToast(message: String) {
        Toast.makeText(getRootView().context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
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