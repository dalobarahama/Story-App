package com.example.storyapp.view.storylist

import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.view.adapter.StoryListAdapter
import com.example.storyapp.view.common.BaseViewMvc

interface StoryListViewMvc : BaseViewMvc<StoryListViewMvc.Listener> {
    interface Listener {
        fun onItemClicked(story: StoryModel, listViewHolder: StoryListAdapter.ListViewHolder)
    }

    fun bindData(lifecycle: Lifecycle, list: PagingData<StoryModel>)
}