package com.example.storyapp.view.detailstory

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.network.RestApiService
import com.example.storyapp.view.common.ViewMvcFactory

class DetailStoryActivity : AppCompatActivity(), DetailStoryMvc.Listener {

    companion object {
        const val STORY_ID = "story_id"
    }

    private lateinit var viewMvc: DetailStoryMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewMvcFactory = ViewMvcFactory(layoutInflater)
        viewMvc = viewMvcFactory.getDetailStoryViewMvc(null)
        setContentView(viewMvc.getRootView())

        val storyId = intent.getStringExtra(STORY_ID)
        val sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "123") ?: ""

        getDetailStory(token, storyId.toString())
    }

    private fun getDetailStory(token: String, storyId: String) {
        val apiService = RestApiService()
        apiService.getDetailStory(token, storyId) {
            if (it?.error == false) {
                viewMvc.bindView(it.story)
            } else {
                viewMvc.showToastError()
            }
        }
    }
}