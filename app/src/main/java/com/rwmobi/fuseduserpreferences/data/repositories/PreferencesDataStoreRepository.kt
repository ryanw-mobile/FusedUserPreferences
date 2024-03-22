/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.repositories

import com.rwmobi.fuseduserpreferences.data.datasources.preferences.PreferencesDataStoreWrapper
import com.rwmobi.fuseduserpreferences.domain.repositories.UserPreferencesRepository

class PreferencesDataStoreRepository(private val preferenceDataStoreWrapper: PreferencesDataStoreWrapper) : UserPreferencesRepository {

    override val stringPreference = preferenceDataStoreWrapper.stringPreference
    override val booleanPreference = preferenceDataStoreWrapper.booleanPreference
    override val intPreference = preferenceDataStoreWrapper.intPreference
    override val preferenceErrors = preferenceDataStoreWrapper.preferenceErrors

    override suspend fun updateStringPreference(newValue: String) {
        preferenceDataStoreWrapper.updateStringPreference(newValue)
    }

    override suspend fun updateBooleanPreference(newValue: Boolean) {
        preferenceDataStoreWrapper.updateBooleanPreference(newValue)
    }

    override suspend fun updateIntPreference(newValue: Int) {
        preferenceDataStoreWrapper.updateIntPreference(newValue)
    }

    override suspend fun clear() {
        preferenceDataStoreWrapper.clear()
    }
}
