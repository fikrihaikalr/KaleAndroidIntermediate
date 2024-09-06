package com.fikrihaikalr.kaleandroidintermediate.data.repository

import com.fikrihaikalr.kaleandroidintermediate.data.remote.datasource.StoryRemoteDataSource
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.AddStoryResponse
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.DetailStoryResponse
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.GetAllStoryResponse
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import com.fikrihaikalr.kaleandroidintermediate.wrapper.proceed
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

interface StoryRepository {
    suspend fun getAllStories(
        token: String,
        page: Int,
        size: Int,
        withLocation: Int
    ): Resource<GetAllStoryResponse>

    suspend fun addNewStory(
        token: String,
        photo: MultipartBody.Part,
        description: RequestBody
    ): Resource<AddStoryResponse>

    suspend fun getDetailStory(token: String, id: String): Resource<DetailStoryResponse>
}

class StoryRepositoryImpl @Inject constructor(private val storyRemoteDataSource: StoryRemoteDataSource): StoryRepository {
    override suspend fun getAllStories(
        token: String,
        page: Int,
        size: Int,
        withLocation: Int
    ): Resource<GetAllStoryResponse> =
        proceed { storyRemoteDataSource.getAllStories(token, page, size, withLocation) }

    override suspend fun addNewStory(
        token: String,
        photo: MultipartBody.Part,
        description: RequestBody
    ): Resource<AddStoryResponse> =
        proceed { storyRemoteDataSource.addNewStory(token, photo, description) }

    override suspend fun getDetailStory(token: String, id: String): Resource<DetailStoryResponse> =
        proceed { storyRemoteDataSource.getDetailStory(token, id) }
}