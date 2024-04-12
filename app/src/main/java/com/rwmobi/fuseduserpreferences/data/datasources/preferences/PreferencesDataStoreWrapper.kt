/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.rwmobi.fuseduserpreferences.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext

class PreferencesDataStoreWrapper(
    private val dataStore: DataStore<Preferences>,
    @DispatcherModule.IoDispatcher private val dispatcher: CoroutineDispatcher,
) {
    private val _preferenceErrors = MutableSharedFlow<Throwable>()
    val preferenceErrors = _preferenceErrors.asSharedFlow()

    fun getDataStoreFlow(): Flow<Preferences> = dataStore.data

    suspend fun <T> updatePreference(key: Preferences.Key<T>, newValue: T) {
        withContext(dispatcher) {
            try {
                dataStore.edit { mutablePreferences ->
                    mutablePreferences[key] = newValue
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
