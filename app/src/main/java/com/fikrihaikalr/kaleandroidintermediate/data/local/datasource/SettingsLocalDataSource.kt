package com.fikrihaikalr.kaleandroidintermediate.data.local.datasource

import com.fikrihaikalr.kaleandroidintermediate.data.local.preferences.SettingsDataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SettingsLocalDataSource{
    fun getTheme(): Flow<Boolean>
    suspend fun setTheme(condition:Boolean)
}

class SettingsLocalDataSourceImpl @Inject constructor(private val settingsDataStoreManager: SettingsDataStoreManager): SettingsLocalDataSource {
    override fun getTheme(): Flow<Boolean> = settingsDataStoreManager.getTheme

    override suspend fun setTheme(condition: Boolean) = settingsDataStoreManager.setTheme(condition)
}