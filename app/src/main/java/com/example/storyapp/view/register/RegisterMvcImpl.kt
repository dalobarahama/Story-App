package com.example.storyapp.view.register

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.storyapp.R

class RegisterMvcImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) : RegisterMvc {

    interface Listener {
        fun onRegisterButtonClicked(name: String, email: String, password: String)
    }

    private val listeners = HashSet<Listener>()

    private val rootView = layoutInflater.inflate(R.layout.activity_register, parent, false)

    private var progressBar: ProgressBar
    private var registerButton: Button
    private var username: EditText
    private var email: EditText
    private var password: EditText
    private var confirmPassword: EditText

    init {
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

    override fun getRootView(): View {
        return rootView
    }

    override fun <T : View?> findViewById(id: Int): T {
        return getRootView().findViewById(id)
    }

    override fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    override fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
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

    private fun getListener(): Listener? {
        var listener: Listener? = null
        for (item in listeners) {
            listener = item
        }
        return listener
    }

    private fun isPasswordSame(): Boolean {
        return password.text.toString() == confirmPassword.text.toString()
    }

}