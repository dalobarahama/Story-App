package com.example.storyapp.view.storylist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.model.StoryModel
import com.example.storyapp.network.RestApiService
import com.example.storyapp.view.adapter.StoryListAdapter
import com.example.storyapp.view.detailstory.DetailStoryActivity

class StoryListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var storyListAdapter: StoryListAdapter

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
        getStories(token)

        Log.d("StoryListFragment", "token: $token")
    }

    private fun getStories(token: String) {
        val apiService = RestApiService()
        apiService.getStories(token) {
            if (it?.error == false) {
                showRecyclerList(it.listStory)
            } else {
                Toast.makeText(context, "Error to load stories", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showRecyclerList(list: List<StoryModel>) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        storyListAdapter = StoryListAdapter(list)
        recyclerView.adapter = storyListAdapter

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