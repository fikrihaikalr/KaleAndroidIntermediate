package com.fikrihaikalr.kaleandroidintermediate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.GetAllStoryResponse
import com.fikrihaikalr.kaleandroidintermediate.data.repository.AuthRepository
import com.fikrihaikalr.kaleandroidintermediate.data.repository.StoryRepository
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _stories = MutableLiveData<Resource<GetAllStoryResponse>>()
    val stories: LiveData<Resource<GetAllStoryResponse>> get() = _stories

    fun getAllStories(token: String, page: Int = 1, size: Int = 20, withLocation: Int = 0){
        _stories.postValue(Resource.Loading())
        viewModelScope.launch {
            _stories.postValue(storyRepository.getAllStories(token, page, size, withLocation))
        }
    }

    fun getToken() = authRepository.getToken().asLiveData()
}