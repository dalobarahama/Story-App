package com.example.storyapp.view.storylist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.view.adapter.StoryListAdapter
import com.example.storyapp.view.detailstory.DetailStoryActivity
import com.example.storyapp.viewmodel.StoryViewModel
import com.example.storyapp.viewmodel.ViewModelFactory

class StoryListFragment : Fragment(), StoryListViewMvc.Listener {
    private val viewModel: StoryViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    private lateinit var viewMvc: StoryListViewMvc

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewMvc = StoryListViewMvcImpl(layoutInflater, container)
        return viewMvc.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStory().observe(viewLifecycleOwner) {
            viewMvc.bindData(lifecycle, it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
    }

    override fun onStop() {
        viewMvc.unregisterListener(this)
        super.onStop()
    }

    override fun onItemClicked(story: StoryModel, listViewHolder: StoryListAdapter.ListViewHolder) {
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            androidx.core.util.Pair(listViewHolder.image, "detail_story_image"),
            androidx.core.util.Pair(listViewHolder.username, "detail_story_username"),
            androidx.core.util.Pair(listViewHolder.description, "detail_story_description"),
        )

        val intent = Intent(context, DetailStoryActivity::class.java)
        intent.putExtra(DetailStoryActivity.STORY_ID, story.id)
        startActivity(intent, optionsCompat.toBundle())
    }
}