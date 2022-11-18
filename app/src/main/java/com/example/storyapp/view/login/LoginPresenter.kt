package com.example.storyapp.view.login

import android.content.Context
import com.example.storyapp.network.RestApiService

class LoginPresenter(private val context: Context) : LoginMVPPresenter {
    private val view: LoginMVPView = LoginActivity()

    override fun onLoginClicked(email: String, password: String) {
         view.showProgressBar()

        val apiService = RestApiService()
        apiService.login(email, password) {
            if (it?.message == "success") {
                view.hideProgressBar()

                val token = "Bearer ${it.loginResult.token}"
                val sharedPref = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("token", token)
                    putString("username", it.loginResult.name)
                    apply()
                }

                view.goToMainActivity()
            } else {
                view.hideProgressBar()
                view.showToast("Email atau password salah")
            }
        }
    }

    override fun goToRegister() {
        view.goToRegisterActivity()
    }
}