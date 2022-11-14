package com.example.storyapp.utils

import androidx.paging.PagingData
import com.example.storyapp.data.model.StoryModel
import com.example.storyapp.data.model.response.StoryResponse
import retrofit2.Response

object DataDummy {
    private fun generateDataDummy(): List<StoryModel> {
        val storyList = ArrayList<StoryModel>()
        for (i in 1..10) {
            val story = StoryModel(
                "2022-11-14T05:40:44.501Z",
                "cobaan",
                "story-$i",
                -6.2342342342342345,
                106.88433877416499,
                "er",
                "https://story-api.dicoding.dev/images/stories/photos-1668404444498_KH0pqpHq.jpg",
            )
            storyList.add(story)
        }
        return storyList
    }

    fun getStoriesDummy(): Response<StoryResponse> {
        val storyList = ArrayList<StoryModel>()
        for (i in 1..10) {
            val story = StoryModel(
                "2022-11-14T05:40:44.501Z",
                "$i",
                "story-$i",
                -6.2342342342342345,
                106.88433877416499,
                "er",
                "https://story-api.dicoding.dev/images/stories/photos-1668404444498_KH0pqpHq.jpg",
            )
            storyList.add(story)
        }
        return Response.success(StoryResponse(true, "Dummy data", storyList))
    }

    fun generatePagingDataDummy(): PagingData<StoryModel> =
        PagingData.from(getStoriesDummy().body()!!.listStory)
}