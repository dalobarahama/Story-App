package com.example.storyapp.view.storylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.view.adapter.LoadingStateAdapter
import com.example.storyapp.view.adapter.StoryListAdapter
import com.example.storyapp.view.common.BaseViewMvcImpl

class StoryListViewMvcImpl(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : StoryListViewMvc, BaseViewMvcImpl<StoryListViewMvc.Listener>() {

    private var recyclerView: RecyclerView

    init {
        setRootView(layoutInflater.inflate(R.layout.fragment_story_list, parent, false))

        recyclerView = findViewById(R.id.recyclerview_story_list)
    }

    override fun bindData(lifecycle: Lifecycle, list: PagingData<StoryModel>) {
        val storyListAdapter = StoryListAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = storyListAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyListAdapter.retry()
            }
        )
        storyListAdapter.submitData(lifecycle, list)
        recyclerView.layoutManager = LinearLayoutManager(getRootView().context)

        storyListAdapter.setOnItemCallback(object : StoryListAdapter.OnItemClickCallback {
            override fun onItemClicked(
                story: StoryModel,
                listViewHolder: StoryListAdapter.ListViewHolder,
            ) {
                getListener()?.onItemClicked(story, listViewHolder)
            }
        })
    }


}