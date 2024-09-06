package com.fikrihaikalr.kaleandroidintermediate.ui.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fikrihaikalr.kaleandroidintermediate.data.repository.AuthRepository
import com.fikrihaikalr.kaleandroidintermediate.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    fun getToken() = authRepository.getToken().asLiveData()
    fun getTheme() = settingsRepository.getTheme().asLiveData()
}