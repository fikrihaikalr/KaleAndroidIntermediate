package com.fikrihaikalr.kaleandroidintermediate.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fikrihaikalr.kaleandroidintermediate.data.repository.AuthRepository
import com.fikrihaikalr.kaleandroidintermediate.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    fun getTheme() : LiveData<Boolean> = settingsRepository.getTheme().asLiveData()

    fun setTheme(condition: Boolean) = viewModelScope.launch {
        settingsRepository.setTheme(condition)
    }

    fun clearToken() = viewModelScope.launch(Dispatchers.IO) { authRepository.clear() }
}