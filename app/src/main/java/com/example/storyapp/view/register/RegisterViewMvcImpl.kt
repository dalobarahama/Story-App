package com.example.storyapp.view.register

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.storyapp.R
import com.example.storyapp.view.common.BaseViewMvcImpl

class RegisterViewMvcImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) : RegisterViewMvc,
    BaseViewMvcImpl<RegisterViewMvc.Listener>() {

    private var progressBar: ProgressBar
    private var registerButton: Button
    private var username: EditText
    private var email: EditText
    private var password: EditText
    private var confirmPassword: EditText

    init {
        setRootView(layoutInflater.inflate(R.layout.activity_register, parent, false))

        progressBar = findViewById(R.id.progress_circular_register)
        registerButton = findViewById(R.id.btn_register)
        username = findViewById(R.id.et_register_name)
        email = findViewById(R.id.et_register_email)
        password = findViewById(R.id.et_register_password)
        confirmPassword = findViewById(R.id.et_register_confirm_password)

        registerButton.setOnClickListener {
            if (isPasswordSame()) {
                getListener()?.onRegisterButtonClicked(
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString()
                )
            } else {
                showToast("Password tidak sama")
            }
        }
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

    private fun isPasswordSame(): Boolean {
        return password.text.toString() == confirmPassword.text.toString()
    }

}