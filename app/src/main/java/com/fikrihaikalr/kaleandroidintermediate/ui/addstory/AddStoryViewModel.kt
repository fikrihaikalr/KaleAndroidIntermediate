package com.fikrihaikalr.kaleandroidintermediate.ui.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.AddStoryResponse
import com.fikrihaikalr.kaleandroidintermediate.data.repository.AuthRepository
import com.fikrihaikalr.kaleandroidintermediate.data.repository.StoryRepository
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {
    private val _addStory = MutableLiveData<Resource<AddStoryResponse>>()
    val addStory: LiveData<Resource<AddStoryResponse>> get() = _addStory

    fun addNewStory(
        token: String,
        photo: MultipartBody.Part,
        description: RequestBody
    ) = viewModelScope.launch(Dispatchers.IO) {
        _addStory.postValue(Resource.Loading())
        val response = storyRepository.addNewStory(token, photo, description)
        viewModelScope.launch(Dispatchers.Main) {
            _addStory.postValue(response)
        }
    }

    fun getToken() = authRepository.getToken().asLiveData()
}