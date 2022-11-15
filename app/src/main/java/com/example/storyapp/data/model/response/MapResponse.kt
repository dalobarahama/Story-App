package com.example.storyapp.data.model.response

import com.example.storyapp.data.model.Result

data class MapResponse(
    val html_attributions: List<Any>,
    val next_page_token: String,
    val results: List<Result>,
    val status: String
)