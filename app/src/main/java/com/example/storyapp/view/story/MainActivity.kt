package com.example.storyapp.view.story

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.R
import com.example.storyapp.view.detailstory.DetailStoryActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tempButton = findViewById<Button>(R.id.temp_button)
        tempButton.setOnClickListener { goToDetailStory() }
    }

    private fun goToDetailStory() {
        startActivity(Intent(this, DetailStoryActivity::class.java))
    }
}