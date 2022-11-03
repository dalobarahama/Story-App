package com.example.storyapp.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.storyapp.R
import com.example.storyapp.view.addstory.AddStoryFragment
import com.example.storyapp.view.profile.ProfileFragment
import com.example.storyapp.view.storylist.StoryListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(StoryListFragment())
        val bnv = findViewById<BottomNavigationView>(R.id.bnv)
        bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu -> {
                    loadFragment(StoryListFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.add_menu -> {
                    loadFragment(AddStoryFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.profile_menu -> {
                    loadFragment(ProfileFragment())
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Exit app?")
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Yes") { _, _ -> super.onBackPressed() }
            .create().show()
    }
}