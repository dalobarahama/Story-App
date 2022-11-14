package com.example.storyapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R

class LoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {

    inner class LoadingStateViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val errorMessage = itemView.findViewById<TextView>(R.id.error_message)
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
        val buttonRetry = itemView.findViewById<Button>(R.id.btn_retry)
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.buttonRetry.setOnClickListener { retry.invoke() }

        if (loadState is LoadState.Error) {
            holder.errorMessage.text = loadState.error.localizedMessage
        }
        holder.errorMessage.isVisible = loadState is LoadState.Error
        holder.buttonRetry.isVisible = loadState is LoadState.Error
        holder.progressBar.isVisible = loadState is LoadState.Loading

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadingStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false)
        return LoadingStateViewHolder(view)
    }
}