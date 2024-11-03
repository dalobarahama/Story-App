package com.example.storyapp.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.storyapp.R
import com.example.storyapp.view.profile.ProfileActivity

class MainActivity : AppCompatActivity(), MainViewMvcImpl.Listener {

    private lateinit var viewMvc: MainViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = MainViewMvcImpl(layoutInflater, null)
        setContentView(viewMvc.getRootView())

        handleOnBackPressed()
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        viewMvc.setDefaultFragment()
    }

    override fun onStop() {
        viewMvc.unregisterListener(this)
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, ProfileActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    override fun loadFragment(fragment: Fragment, container: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(container, fragment)
        transaction.commit()
    }

    override fun onPositiveDialogPressed() {
        finish()
    }

    private fun handleOnBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewMvc.showAlertDialog()
                }
            })
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}