package com.fikrihaikalr.kaleandroidintermediate.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.fikrihaikalr.kaleandroidintermediate.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    val getTheme: Flow<Boolean> = dataStore.data.map {
        it[booleanPreferencesKey(Constants.THEME_KEY)] ?: false
    }

    suspend fun setTheme(condition: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(Constants.THEME_KEY)] = condition
        }
    }
}