package com.example.storyapp.view.addstory

import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.example.storyapp.view.common.BaseViewMvc

interface AddStoryViewMvc: BaseViewMvc<AddStoryViewMvc.Listener> {
    interface Listener {
        fun uploadStory(description: String, imageUri: Uri)
        fun openGallery()
        fun openCamera()
    }

    fun showProgressBar()
    fun hideProgressBar()
    fun showToast(message: String)
    fun isImageAndDescriptionIsNull(): Boolean
    fun loadImage(uri: Uri)
    fun selectStoryListFragment(requireActivity: FragmentActivity)
}