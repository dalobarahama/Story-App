package com.example.storyapp.view.storylist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.view.adapter.LoadingStateAdapter
import com.example.storyapp.view.adapter.StoryListAdapter
import com.example.storyapp.view.detailstory.DetailStoryActivity
import com.example.storyapp.viewmodel.StoryViewModel
import com.example.storyapp.viewmodel.ViewModelFactory

class StoryListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: StoryViewModel by viewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_story_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerview_story_list)
        recyclerView.setHasFixedSize(true)

        val sharedPref = view.context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "123") ?: ""

        viewModel.getStory(token).observe(viewLifecycleOwner) {
            showRecyclerList(it)
        }
        Log.d("StoryListFragment", "token: $token")
    }

    private fun showRecyclerList(list: PagingData<StoryModel>) {
        Log.d("StoryListFragment", "list: $list")
        val storyListAdapter = StoryListAdapter()
        recyclerView.adapter = storyListAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyListAdapter.retry()
            }
        )
        storyListAdapter.submitData(lifecycle, list)
        recyclerView.layoutManager = LinearLayoutManager(context)

        storyListAdapter.setOnItemCallback(object : StoryListAdapter.OnItemClickCallback {
            override fun onItemClicked(
                story: StoryModel,
                listViewHolder: StoryListAdapter.ListViewHolder,
            ) {
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    Pair(listViewHolder.image, "detail_story_image"),
                    Pair(listViewHolder.username, "detail_story_username"),
                    Pair(listViewHolder.description, "detail_story_description"),
                )

                val intent = Intent(context, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.STORY_ID, story.id)
                startActivity(intent, optionsCompat.toBundle())
            }
        })
    }
}