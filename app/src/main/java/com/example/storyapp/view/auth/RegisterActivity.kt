package com.example.storyapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.customview.MyEditText
import com.example.storyapp.network.RestApiService

class RegisterActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var password: MyEditText
    private lateinit var confirmPassword: MyEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        progressBar = findViewById(R.id.progress_circular_register)

        val registerButton = findViewById<Button>(R.id.btn_register)
        val username = findViewById<EditText>(R.id.et_register_name)
        val email = findViewById<EditText>(R.id.et_register_email)
        password = findViewById(R.id.et_register_password)
        confirmPassword = findViewById(R.id.et_register_confirm_password)

        registerButton.setOnClickListener {
            if (isPasswordSame()) {
                progressBar.visibility = View.VISIBLE
                register(
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString()
                )
            } else {
                Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isPasswordSame(): Boolean {
        return password.text.toString() == confirmPassword.text.toString()
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun register(name: String, email: String, password: String) {
        val apiService = RestApiService()
        apiService.register(name, email, password) {
            if (it?.error == false) {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
                goToLogin()
            } else {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Failed to register user", Toast.LENGTH_SHORT).show()
            }
        }
    }
}