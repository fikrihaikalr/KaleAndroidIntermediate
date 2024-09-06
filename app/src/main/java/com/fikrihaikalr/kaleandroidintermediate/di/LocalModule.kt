package com.fikrihaikalr.kaleandroidintermediate.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.fikrihaikalr.kaleandroidintermediate.data.local.preferences.AuthDataStoreManager
import com.fikrihaikalr.kaleandroidintermediate.data.local.preferences.SettingsDataStoreManager
import com.fikrihaikalr.kaleandroidintermediate.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.datastorePreferences by preferencesDataStore(
    name = Constants.DATASTORE_PREFERENCES
)

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.datastorePreferences

    @Singleton
    @Provides
    fun provideAuthDataStoreManager(dataStore: DataStore<Preferences>): AuthDataStoreManager =
        AuthDataStoreManager(dataStore)

    @Singleton
    @Provides
    fun provideSettingDataStoreManager(dataStore: DataStore<Preferences>): SettingsDataStoreManager =
        SettingsDataStoreManager(dataStore)
}