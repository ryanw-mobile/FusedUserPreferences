/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PreferencesDataStoreWrapper(
    private val dataStore: DataStore<Preferences>,
    externalCoroutineScope: CoroutineScope,
    private val stringPreferenceDefault: String = "",
    private val booleanPreferenceDefault: Boolean = false,
    private val intPreferenceDefault: Int = 0,
) : com.rwmobi.fuseduserpreferences.data.datasources.preferences.Preferences {
    private val _stringPreference = MutableStateFlow(stringPreferenceDefault)
    override val stringPreference = _stringPreference.asStateFlow()

    private val _booleanPreference = MutableStateFlow(booleanPreferenceDefault)
    override val booleanPreference = _booleanPreference.asStateFlow()

    private val _intPreference = MutableStateFlow(intPreferenceDefault)
    override val intPreference = _intPreference.asStateFlow()

    private val _preferenceErrors = MutableSharedFlow<Throwable>()
    override val preferenceErrors = _preferenceErrors.asSharedFlow()

    companion object {
        val DATASTORE_PREF_KEY_STRING = stringPreferencesKey(PREF_KEY_STRING)
        val DATASTORE_PREF_KEY_BOOLEAN = booleanPreferencesKey(PREF_KEY_BOOLEAN)
        val DATASTORE_PREF_KEY_INT = intPreferencesKey(PREF_KEY_INT)
    }

    init {
        externalCoroutineScope.launch {
            dataStore.data.catch { exception ->
                _preferenceErrors.emit(exception)
            }
                .collect { prefs ->
                    _stringPreference.value = prefs[DATASTORE_PREF_KEY_STRING] ?: stringPreferenceDefault
                    _booleanPreference.value = prefs[DATASTORE_PREF_KEY_BOOLEAN] ?: booleanPreferenceDefault
                    _intPreference.value = prefs[DATASTORE_PREF_KEY_INT] ?: intPreferenceDefault
                }
        }
    }

    override suspend fun updateStringPreference(newValue: String) {
        try {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[DATASTORE_PREF_KEY_STRING] = newValue
            }
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    override suspend fun updateBooleanPreference(newValue: Boolean) {
        try {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[DATASTORE_PREF_KEY_BOOLEAN] = newValue
            }
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    override suspend fun updateIntPreference(newValue: Int) {
        try {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[DATASTORE_PREF_KEY_INT] = newValue
            }
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    override suspend fun clear() {
        try {
            dataStore.edit { mutablePreferences ->
                mutablePreferences.clear()
            }
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }
}
