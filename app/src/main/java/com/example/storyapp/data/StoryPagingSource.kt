package com.example.storyapp.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.network.ApiService

class StoryPagingSource(private val apiService: ApiService, private val token: String) :
    PagingSource<Int, StoryModel>() {

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, StoryModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryModel> {
        Log.d("StoryPagingSource", "load: called")
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val storyList = apiService.getStories(token, page, params.loadSize).body()?.listStory

            Log.d("StoryPagingSource", "load size: ${params.loadSize}")
            Log.d("StoryPagingSource", "load list: ${storyList?.size}")
            Log.d("StoryPagingSource", "story name: ${storyList!![0].name}")
            Log.d("StoryPagingSource", "page: $page")
            LoadResult.Page(
                data = storyList,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            Log.d("StoryPagingSource", "catch: ${e.message}")
            Log.d("StoryPagingSource", "catch: ${e.cause}")
            LoadResult.Error(e)
        }
    }

}