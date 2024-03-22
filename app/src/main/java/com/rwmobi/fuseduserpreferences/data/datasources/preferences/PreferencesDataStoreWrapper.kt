/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.rwmobi.fuseduserpreferences.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PreferencesDataStoreWrapper(
    private val dataStore: DataStore<Preferences>,
    private val prefKeyString: Preferences.Key<String>,
    private val prefKeyBoolean: Preferences.Key<Boolean>,
    private val prefKeyInt: Preferences.Key<Int>,
    private val stringPreferenceDefault: String,
    private val booleanPreferenceDefault: Boolean,
    private val intPreferenceDefault: Int,
    externalCoroutineScope: CoroutineScope,
    @DispatcherModule.IoDispatcher private val dispatcher: CoroutineDispatcher,
) {
    private val _stringPreference = MutableStateFlow(stringPreferenceDefault)
    val stringPreference = _stringPreference.asStateFlow()

    private val _booleanPreference = MutableStateFlow(booleanPreferenceDefault)
    val booleanPreference = _booleanPreference.asStateFlow()

    private val _intPreference = MutableStateFlow(intPreferenceDefault)
    val intPreference = _intPreference.asStateFlow()

    private val _preferenceErrors = MutableSharedFlow<Throwable>()
    val preferenceErrors = _preferenceErrors.asSharedFlow()

    init {
        externalCoroutineScope.launch(dispatcher) {
            dataStore.data.catch { exception ->
                _preferenceErrors.emit(exception)
            }
                .collect { prefs ->
                    _stringPreference.value = prefs[prefKeyString] ?: stringPreferenceDefault
                    _booleanPreference.value = prefs[prefKeyBoolean] ?: booleanPreferenceDefault
                    _intPreference.value = prefs[prefKeyInt] ?: intPreferenceDefault
                }
        }
    }

    suspend fun updateStringPreference(newValue: String) {
        withContext(dispatcher) {
            try {
                dataStore.edit { mutablePreferences ->
                    mutablePreferences[prefKeyString] = newValue
                }
            } catch (e: Throwable) {
                _preferenceErrors.emit(e)
            }
        }
    }

    suspend fun updateBooleanPreference(newValue: Boolean) {
        withContext(dispatcher) {
            try {
                dataStore.edit { mutablePreferences ->
                    mutablePreferences[prefKeyBoolean] = newValue
                }
            } catch (e: Throwable) {
                _preferenceErrors.emit(e)
            }
        }
    }

    suspend fun updateIntPreference(newValue: Int) {
        withContext(dispatcher) {
            try {
                dataStore.edit { mutablePreferences ->
                    mutablePreferences[prefKeyInt] = newValue
                }
            } catch (e: Throwable) {
                _preferenceErrors.emit(e)
            }
        }
    }

    suspend fun clear() {
        withContext(dispatcher) {
            try {
                dataStore.edit { mutablePreferences ->
                    mutablePreferences.clear()
                }
            } catch (e: Throwable) {
                _preferenceErrors.emit(e)
            }
        }
    }
}
