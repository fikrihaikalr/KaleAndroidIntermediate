package com.fikrihaikalr.kaleandroidintermediate.data.remote.datasource

import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.AddStoryResponse
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.DetailStoryResponse
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.GetAllStoryResponse
import com.fikrihaikalr.kaleandroidintermediate.data.remote.service.StoryApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

interface StoryRemoteDataSource {
    suspend fun getAllStories(
        token:String,
        page: Int,
        size: Int,
        withLocation: Int
    ): GetAllStoryResponse

    suspend fun addNewStory(
        token: String,
        photo: MultipartBody.Part,
        description: RequestBody
    ): AddStoryResponse

    suspend fun getDetailStory(token: String, id:String): DetailStoryResponse
}

class StoryRemoteDataSourceImpl @Inject constructor(private val storyApiService: StoryApiService) :
    StoryRemoteDataSource {
    override suspend fun getAllStories(
        token: String,
        page: Int,
        size: Int,
        withLocation: Int
    ): GetAllStoryResponse = storyApiService.getAllStories(token, page, size, withLocation)

    override suspend fun addNewStory(
        token: String,
        photo: MultipartBody.Part,
        description: RequestBody
    ): AddStoryResponse = storyApiService.addNewStory(token, photo, description)

    override suspend fun getDetailStory(token: String, id: String): DetailStoryResponse =
        storyApiService.getDetailStory(token, id)
}