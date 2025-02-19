package com.example.storyapp.view.detailstory

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.view.common.BaseViewMvcImpl

class DetailStoryMvcImpl(layoutInflater: LayoutInflater, parent: ViewGroup?): DetailStoryMvc, BaseViewMvcImpl<DetailStoryMvc.Listener>() {

    private var image: ImageView
    private var description: TextView
    private var username: TextView

    init {
        setRootView(layoutInflater.inflate(R.layout.activity_detail_story, parent, false))

        username = findViewById(R.id.tv_detail_story_username)
        description = findViewById(R.id.tv_detail_story_description)
        image = findViewById(R.id.detail_story_image)
    }

    override fun bindView(model: StoryModel) {
        username.text = model.name
        description.text = model.description
        Glide.with(getRootView().context)
            .load(model.photoUrl)
            .into(image)
    }

    override fun showToastError() {
        Toast.makeText(getRootView().context, "Error", Toast.LENGTH_SHORT).show()
    }
}