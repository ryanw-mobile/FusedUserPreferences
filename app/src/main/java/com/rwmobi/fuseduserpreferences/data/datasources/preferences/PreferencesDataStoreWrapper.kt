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
    prefKeyString: String,
    prefKeyBoolean: String,
    prefKeyInt: String,
    private val stringPreferenceDefault: String = "",
    private val booleanPreferenceDefault: Boolean = false,
    private val intPreferenceDefault: Int = 0,
) : com.rwmobi.fuseduserpreferences.data.datasources.preferences.Preferences {
    private val datastorePrefKeyString = stringPreferencesKey(prefKeyString)
    private val datastorePrefKeyBoolean = booleanPreferencesKey(prefKeyBoolean)
    private val datastorePrefKeyInt = intPreferencesKey(prefKeyInt)

    private val _stringPreference = MutableStateFlow(stringPreferenceDefault)
    override val stringPreference = _stringPreference.asStateFlow()

    private val _booleanPreference = MutableStateFlow(booleanPreferenceDefault)
    override val booleanPreference = _booleanPreference.asStateFlow()

    private val _intPreference = MutableStateFlow(intPreferenceDefault)
    override val intPreference = _intPreference.asStateFlow()

    private val _preferenceErrors = MutableSharedFlow<Throwable>()
    override val preferenceErrors = _preferenceErrors.asSharedFlow()

    init {
        externalCoroutineScope.launch {
            dataStore.data.catch { exception ->
                _preferenceErrors.emit(exception)
            }
                .collect { prefs ->
                    _stringPreference.value = prefs[datastorePrefKeyString] ?: stringPreferenceDefault
                    _booleanPreference.value = prefs[datastorePrefKeyBoolean] ?: booleanPreferenceDefault
                    _intPreference.value = prefs[datastorePrefKeyInt] ?: intPreferenceDefault
                }
        }
    }

    override suspend fun updateStringPreference(newValue: String) {
        try {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[datastorePrefKeyString] = newValue
            }
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    override suspend fun updateBooleanPreference(newValue: Boolean) {
        try {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[datastorePrefKeyBoolean] = newValue
            }
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    override suspend fun updateIntPreference(newValue: Int) {
        try {
            dataStore.edit { mutablePreferences ->
                mutablePreferences[datastorePrefKeyInt] = newValue
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
