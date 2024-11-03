package com.example.storyapp.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.network.RestApiService
import com.example.storyapp.view.common.ViewMvcFactory
import com.example.storyapp.view.main.MainActivity
import com.example.storyapp.view.register.RegisterActivity

class LoginActivity : AppCompatActivity(), LoginViewMvc.Listener {

    private lateinit var viewMvc: LoginViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewMvcFactory = ViewMvcFactory(layoutInflater)
        viewMvc = viewMvcFactory.getLoginViewMvc(null)
        setContentView(viewMvc.getRootView())
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
    }

    override fun onStop() {
        viewMvc.unregisterListener(this)
        super.onStop()
    }

    override fun onClickRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun onClickLogin(email: String, password: String) {
        viewMvc.showProgressBar()
        val apiService = RestApiService()
        apiService.login(email, password) {
            if (it?.message == "success") {
                viewMvc.hideProgressBar()

                val token = "Bearer ${it.loginResult.token}"
                val sharedPref = this.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("token", token)
                    putString("username", it.loginResult.name)
                    apply()
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                viewMvc.hideProgressBar()
                viewMvc.showToast("Email atau password salah")
            }
        }
    }
}