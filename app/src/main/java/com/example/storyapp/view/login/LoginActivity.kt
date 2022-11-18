package com.example.storyapp.view.login

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

class LoginActivity : AppCompatActivity(), LoginMVPView {
    private lateinit var progressBar: ProgressBar
    private lateinit var loginButton: Button
    private lateinit var register: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: MyEditText
    private lateinit var image: ImageView
    private lateinit var tvLogin: TextView

    private lateinit var presenter: LoginMVPPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        setOnClickListener()
        playAnimation()
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

    private fun init(){
        presenter = LoginPresenter(this)

        progressBar = findViewById(R.id.progress_circular_login)
        loginButton = findViewById(R.id.btn_login)
        register = findViewById(R.id.tv_register)
        etEmail = findViewById(R.id.et_login_email)
        etPassword = findViewById(R.id.et_login_password)
        image = findViewById(R.id.iv_login_logo)
        tvLogin = findViewById(R.id.tv_login)
    }

    private fun setOnClickListener() {
        loginButton.setOnClickListener {
            presenter.onLoginClicked(etEmail.text.toString(), etPassword.text.toString())
        }
        register.setOnClickListener {
            presenter.goToRegister()
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun goToRegisterActivity() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}