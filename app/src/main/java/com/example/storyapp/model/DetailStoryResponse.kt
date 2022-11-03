package com.example.storyapp.model

data class DetailStoryResponse(
    val error: Boolean,
    val message: String,
    val story: StoryModel
)