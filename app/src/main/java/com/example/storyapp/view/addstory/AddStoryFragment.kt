package com.example.storyapp.view.addstory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.storyapp.R

class AddStoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_story, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_circular_add_story)
        val imageView = view.findViewById<ImageView>(R.id.add_story_image)
        val uploadButton = view.findViewById<Button>(R.id.btn_add_story_upload)
        uploadButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            Glide.with(this)
                .load(R.drawable.ic_launcher_background)
                .into(imageView)
        }
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            AddStoryFragment().apply {

            }
    }
}