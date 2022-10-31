package com.example.storyapp.view.storylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storyapp.R

class StoryListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_story_list, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            StoryListFragment().apply {

            }
    }
}