/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.repositories

import com.rwmobi.fuseduserpreferences.data.datasources.preferences.Preferences
import com.rwmobi.fuseduserpreferences.di.DispatcherModule
import com.rwmobi.fuseduserpreferences.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

// This looks a bit redundant, but ChatGPT says this confirms to the Clean Architecture
class UserPreferencesRepositoryImpl(
    private val preferences: Preferences,
    @DispatcherModule.IoDispatcher private val dispatcher: CoroutineDispatcher,
) : UserPreferencesRepository {

    override val stringPreference = preferences.stringPreference
    override val booleanPreference = preferences.booleanPreference
    override val intPreference = preferences.intPreference
    override val preferenceErrors = preferences.preferenceErrors

    override suspend fun updateStringPreference(newValue: String) {
        withContext(dispatcher) {
            preferences.updateStringPreference(newValue)
        }
    }

    override suspend fun updateBooleanPreference(newValue: Boolean) {
        withContext(dispatcher) {
            preferences.updateBooleanPreference(newValue)
        }
    }

    override suspend fun updateIntPreference(newValue: Int) {
        withContext(dispatcher) {
            preferences.updateIntPreference(newValue)
        }
    }

    override suspend fun clear() {
        withContext(dispatcher) {
            preferences.clear()
        }
    }
}
