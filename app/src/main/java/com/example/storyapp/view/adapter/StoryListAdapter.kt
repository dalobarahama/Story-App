package com.example.storyapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.data.model.StoryModel

class StoryListAdapter :
    PagingDataAdapter<StoryModel, StoryListAdapter.ListViewHolder>(DIFF_CALLBACK) {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username = itemView.findViewById<TextView>(R.id.story_username)
        var description = itemView.findViewById<TextView>(R.id.story_description)
        var image = itemView.findViewById<ImageView>(R.id.story_image)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        Log.d("StoryListAdapter", "onCreateViewHolder called")
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val story = getItem(position)
        Log.d("StoryListAdapter", "story: ${getItem(position)}")
        Log.d("StoryListAdapter", "story: ${story?.name}")

        holder.username.text = story?.name
        holder.description.text = story?.description
        Glide.with(holder.itemView.context)
            .load(story?.photoUrl)
            .into(holder.image)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(story!!, holder) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryModel>() {
            override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean =
                oldItem == newItem

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(story: StoryModel, listViewHolder: ListViewHolder)
    }
}