/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.prefdatastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rwmobi.fuseduserpreferences.data.datasources.AppPreferences
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_BOOLEAN
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_INT
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_STRING
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PreferenceDataStore(val dataStore: DataStore<Preferences>, dispatcher: CoroutineDispatcher) : AppPreferences {
    private val stringPreferenceDefault = ""
    private val booleanPreferenceDefault = false
    private val intPreferenceDefault = 0

    private val _stringPreference = MutableStateFlow(stringPreferenceDefault)
    override val stringPreference = _stringPreference.asStateFlow()

    private val _booleanPreference = MutableStateFlow(booleanPreferenceDefault)
    override val booleanPreference = _booleanPreference.asStateFlow()

    private val _intPreference = MutableStateFlow(intPreferenceDefault)
    override val intPreference = _intPreference.asStateFlow()

    companion object {
        val DATASTORE_PREF_KEY_STRING = stringPreferencesKey(PREF_KEY_STRING)
        val DATASTORE_PREF_KEY_BOOLEAN = booleanPreferencesKey(PREF_KEY_BOOLEAN)
        val DATASTORE_PREF_KEY_INT = intPreferencesKey(PREF_KEY_INT)
    }

    init {
        CoroutineScope(dispatcher).launch {
            dataStore.data.catch { exception ->
                // Handle dataStore.data flow exceptions here
                exception.printStackTrace()
            }
                .collect { prefs ->
                    _stringPreference.value = prefs[DATASTORE_PREF_KEY_STRING] ?: stringPreferenceDefault
                    _booleanPreference.value = prefs[DATASTORE_PREF_KEY_BOOLEAN] ?: booleanPreferenceDefault
                    _intPreference.value = prefs[DATASTORE_PREF_KEY_INT] ?: intPreferenceDefault
                }
        }
    }

    override suspend fun updateStringPreference(newValue: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[DATASTORE_PREF_KEY_STRING] = newValue
        }
    }

    override suspend fun updateBooleanPreference(newValue: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[DATASTORE_PREF_KEY_BOOLEAN] = newValue
        }
    }

    override suspend fun updateIntPreference(newValue: Int) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[DATASTORE_PREF_KEY_INT] = newValue
        }
    }

    override suspend fun clear() {
        dataStore.edit { mutablePreferences ->
            mutablePreferences.clear()
        }
    }
}
