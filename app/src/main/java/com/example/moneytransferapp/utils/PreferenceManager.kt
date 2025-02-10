package com.example.moneytransferapp.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings_prefs")

class PreferencesManager(context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val IS_FIRST_RUN = booleanPreferencesKey("is_first_run")
    }

    val isFirstRun: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.IS_FIRST_RUN] ?: true } // Default: true

    suspend fun setFirstRunCompleted() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_FIRST_RUN] = false
        }
    }
}

