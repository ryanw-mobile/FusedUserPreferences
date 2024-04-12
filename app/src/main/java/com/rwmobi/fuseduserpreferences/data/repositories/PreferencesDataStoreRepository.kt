/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.repositories

import androidx.datastore.preferences.core.Preferences
import com.rwmobi.fuseduserpreferences.data.datasources.preferences.PreferencesDataStoreWrapper
import com.rwmobi.fuseduserpreferences.di.DispatcherModule
import com.rwmobi.fuseduserpreferences.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PreferencesDataStoreRepository(
    private val preferenceDataStoreWrapper: PreferencesDataStoreWrapper,
    private val prefKeyString: Preferences.Key<String>,
    private val prefKeyBoolean: Preferences.Key<Boolean>,
    private val prefKeyInt: Preferences.Key<Int>,
    private val stringPreferenceDefault: String,
    private val booleanPreferenceDefault: Boolean,
    private val intPreferenceDefault: Int,
    externalCoroutineScope: CoroutineScope,
    @DispatcherModule.MainDispatcher private val dispatcher: CoroutineDispatcher,
) : UserPreferencesRepository {

    private val _stringPreference = MutableStateFlow(stringPreferenceDefault)
    override val stringPreference = _stringPreference.asStateFlow()

    private val _booleanPreference = MutableStateFlow(booleanPreferenceDefault)
    override val booleanPreference = _booleanPreference.asStateFlow()

    private val _intPreference = MutableStateFlow(intPreferenceDefault)
    override val intPreference = _intPreference.asStateFlow()

    private val _preferenceErrors = MutableSharedFlow<Throwable>()
    override val preferenceErrors = _preferenceErrors.asSharedFlow()

    init {
        externalCoroutineScope.launch(dispatcher) {
            launch {
                preferenceDataStoreWrapper.preferenceErrors.collect { _preferenceErrors.emit(it) }
            }

            launch {
                preferenceDataStoreWrapper.getDataStoreFlow()
                    .catch { exception ->
                        _preferenceErrors.emit(exception)
                    }
                    .collect { prefs ->
                        _stringPreference.value = prefs[prefKeyString] ?: stringPreferenceDefault
                        _booleanPreference.value = prefs[prefKeyBoolean] ?: booleanPreferenceDefault
                        _intPreference.value = prefs[prefKeyInt] ?: intPreferenceDefault
                    }
            }
        }
    }

    override suspend fun updateStringPreference(newValue: String) {
        preferenceDataStoreWrapper.updatePreference(key = prefKeyString, newValue = newValue)
    }

    override suspend fun updateBooleanPreference(newValue: Boolean) {
        preferenceDataStoreWrapper.updatePreference(key = prefKeyBoolean, newValue = newValue)
    }

    override suspend fun updateIntPreference(newValue: Int) {
        preferenceDataStoreWrapper.updatePreference(key = prefKeyInt, newValue = newValue)
    }

    override suspend fun clear() {
        preferenceDataStoreWrapper.clear()
    }
}
