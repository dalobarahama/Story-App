package com.example.storyapp.view.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.storyapp.R


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = view.context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "username") ?: ""

        val textViewUsername = view.findViewById<TextView>(R.id.tv_profile_username)
        textViewUsername.text = username

        val buttonLogout = view.findViewById<Button>(R.id.btn_profile_logout)
        buttonLogout.setOnClickListener {
            sharedPref.edit().clear().apply()
            activity?.finish()
        }
    }
}