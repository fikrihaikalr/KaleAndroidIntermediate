package com.fikrihaikalr.kaleandroidintermediate.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.RegisterBody
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.RegisterResponse
import com.fikrihaikalr.kaleandroidintermediate.data.repository.AuthRepository
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _register = MutableLiveData<Resource<RegisterResponse>>()
    val register : LiveData<Resource<RegisterResponse>> get() = _register

    fun register(registerBody: RegisterBody){
        _register.postValue(Resource.Loading())
        viewModelScope.launch{
            _register.postValue(authRepository.register(registerBody))
        }
    }
}