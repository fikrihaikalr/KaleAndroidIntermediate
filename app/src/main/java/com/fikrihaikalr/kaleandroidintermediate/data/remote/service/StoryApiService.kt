package com.fikrihaikalr.kaleandroidintermediate.data.remote.service

import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.AddStoryResponse
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.DetailStoryResponse
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.GetAllStoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface StoryApiService {
    @GET(STORIES)
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("size") size: Int = SIZE_PER_PAGE,
        @Query("location") withLocation: Int = 0
    ): GetAllStoryResponse

    @Multipart
    @POST(STORIES)
    suspend fun addNewStory(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): AddStoryResponse

    @GET("$STORIES/{id}")
    suspend fun getDetailStory(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): DetailStoryResponse

    companion object {
        const val STORIES = "stories"
        const val DEFAULT_PAGE = 1
        const val SIZE_PER_PAGE = 10
    }
}