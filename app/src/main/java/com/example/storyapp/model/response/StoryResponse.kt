package com.example.storyapp.model.response

import com.example.storyapp.model.StoryModel

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<StoryModel>
)