package com.example.storyapp.view.addstory

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.view.common.BaseViewMvcImpl

class AddStoryViewMvcImpl(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : AddStoryViewMvc, BaseViewMvcImpl<AddStoryViewMvc.Listener>() {

    private lateinit var imageView: ImageView
    private lateinit var imageUri: Uri
    private lateinit var progressBar: ProgressBar
    private lateinit var description: EditText

    init {
        setRootView(layoutInflater.inflate(R.layout.fragment_add_story, parent, false))

        progressBar = findViewById(R.id.progress_circular_add_story)
        imageView = findViewById(R.id.add_story_image)
        description = findViewById(R.id.et_add_story_description)
        val uploadButton = findViewById<Button>(R.id.btn_add_story_upload)
        val openCameraButton = findViewById<Button>(R.id.btn_add_story_image_camera)
        val openGalleryButton = findViewById<Button>(R.id.btn_add_story_image_gallery)

        openCameraButton.setOnClickListener {
            getListener()?.openCamera()
        }

        openGalleryButton.setOnClickListener {
            getListener()?.openGallery()
        }

        uploadButton.setOnClickListener {
            getListener()?.uploadStory(description.text.toString(), imageUri)
        }


    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun showToast(message: String) {
        Toast.makeText(getRootView().context, message, Toast.LENGTH_SHORT).show()
    }

    override fun isImageAndDescriptionIsNull(): Boolean {
        return imageView.drawable == null && description.text == null
    }

    override fun loadImage(uri: Uri) {
        Glide.with(getRootView().context)
            .load(uri)
            .into(imageView)

        imageUri = uri
    }
}