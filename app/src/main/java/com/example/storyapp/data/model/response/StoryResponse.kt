package com.example.storyapp.data.model.response

import com.example.storyapp.data.model.StoryModel

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<StoryModel>
)