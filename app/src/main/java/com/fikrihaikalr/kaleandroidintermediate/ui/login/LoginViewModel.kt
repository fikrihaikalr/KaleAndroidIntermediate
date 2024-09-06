package com.fikrihaikalr.kaleandroidintermediate.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.LoginBody
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.LoginResponse
import com.fikrihaikalr.kaleandroidintermediate.data.repository.AuthRepository
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    private val _login = MutableLiveData<Resource<LoginResponse>>()
    val login: LiveData<Resource<LoginResponse>> get() = _login

    fun login(loginBody: LoginBody){
        _login.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val response = authRepository.login(loginBody)
            viewModelScope.launch(Dispatchers.Main) {
                _login.postValue(response)
            }
        }
    }
}