package com.fikrihaikalr.kaleandroidintermediate.data.repository

import com.fikrihaikalr.kaleandroidintermediate.data.local.datasource.SettingsLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SettingsRepository {
    fun getTheme(): Flow<Boolean>
    suspend fun setTheme(condition:Boolean)
}

class SettingsRepositoryImpl @Inject constructor(private val settingsLocalDataSource: SettingsLocalDataSource): SettingsRepository {
    override fun getTheme(): Flow<Boolean> = settingsLocalDataSource.getTheme()

    override suspend fun setTheme(condition: Boolean) = settingsLocalDataSource.setTheme(condition)
}