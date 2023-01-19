package com.app.testexample.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "trukker_data")

class AppPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val appContext = context.applicationContext

    companion object {
        private val AUTH_TOKEN = stringPreferencesKey("auth_token")
        private val USER_NAME = stringPreferencesKey("user_name")
    }

    val getAuthToken: Flow<String> = appContext.dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN] ?: ""
    }

    suspend fun saveAuthTokens(token: String) {
        appContext.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }

    suspend fun clear() {
        appContext.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
