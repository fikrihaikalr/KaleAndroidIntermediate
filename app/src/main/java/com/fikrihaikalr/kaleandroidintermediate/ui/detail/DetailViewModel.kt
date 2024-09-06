package com.fikrihaikalr.kaleandroidintermediate.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.DetailStoryResponse
import com.fikrihaikalr.kaleandroidintermediate.data.repository.AuthRepository
import com.fikrihaikalr.kaleandroidintermediate.data.repository.StoryRepository
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _detailStory = MutableLiveData<Resource<DetailStoryResponse>>()
    val detailStory: LiveData<Resource<DetailStoryResponse>> get() = _detailStory

    fun getDetailStory(token: String, id: String){
        _detailStory.postValue(Resource.Loading())
        viewModelScope.launch {
            _detailStory.postValue(storyRepository.getDetailStory(token, id))
        }
    }

    fun getToken() = authRepository.getToken().asLiveData()
}