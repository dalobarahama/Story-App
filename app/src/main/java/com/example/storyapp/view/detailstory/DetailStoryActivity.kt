package com.example.storyapp.view.detailstory

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.network.RestApiService

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var description: TextView
    private lateinit var username: TextView

    companion object {
        const val STORY_ID = "story_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_story)

        username = findViewById(R.id.tv_detail_story_username)
        description = findViewById(R.id.tv_detail_story_description)
        image = findViewById(R.id.detail_story_image)

        val storyId = intent.getStringExtra(STORY_ID)
        val sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "123") ?: ""

        getDetailStory(token, storyId.toString())
    }

    private fun getDetailStory(token: String, storyId: String) {
        val apiService = RestApiService()
        apiService.getDetailStory(token, storyId) {
            if (it?.error == false) {
                username.text = it.story.name
                description.text = it.story.description
                Glide.with(this)
                    .load(it.story.photoUrl)
                    .into(image)
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}