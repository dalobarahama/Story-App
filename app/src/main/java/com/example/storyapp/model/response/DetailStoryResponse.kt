package com.example.storyapp.model.response

import com.example.storyapp.model.StoryModel

data class DetailStoryResponse(
    val error: Boolean,
    val message: String,
    val story: StoryModel
)