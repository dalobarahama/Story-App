package com.example.storyapp.view.detailstory

import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.view.common.BaseViewMvc

interface DetailStoryMvc: BaseViewMvc<DetailStoryMvc.Listener> {
    interface Listener {

    }

    fun bindView(model: StoryModel)
    fun showToastError()
}