package com.example.storyapp.view.addstory

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyapp.R

class AddStoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_story)

        val progressBar = findViewById<ProgressBar>(R.id.progress_circular_add_story)
        val imageView = findViewById<ImageView>(R.id.add_story_image)
        val uploadButton = findViewById<Button>(R.id.btn_add_story_upload)
        uploadButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            Glide.with(this)
                .load(R.drawable.ic_launcher_background)
                .into(imageView)
        }

    }
}