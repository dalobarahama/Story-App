package com.example.storyapp.view.register

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.customview.MyEditText
import com.example.storyapp.network.RestApiService
import com.example.storyapp.view.login.LoginActivity

class RegisterActivity : AppCompatActivity(), RegisterMvcImpl.Listener {

    private lateinit var viewMvc: RegisterMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewMvc = RegisterMvcImpl(layoutInflater, null)

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

    override fun onRegisterButtonClicked(name: String, email: String, password: String) {
        viewMvc.showProgressBar()
        val apiService = RestApiService()
        apiService.register(name, email, password) {
            if (it?.error == false) {
                viewMvc.hideProgressBar()
                viewMvc.showToast("User created")
                goToLogin()
            } else {
                viewMvc.hideProgressBar()
                viewMvc.showToast("Failed to register user")
            }
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}