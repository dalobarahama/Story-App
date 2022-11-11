package com.example.storyapp.data.model.response

import com.example.storyapp.data.model.StoryModel

data class DetailStoryResponse(
    val error: Boolean,
    val message: String,
    val story: StoryModel
)