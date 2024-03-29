/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.domain.repositories

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface UserPreferencesRepository {
    val stringPreference: StateFlow<String>
    val booleanPreference: StateFlow<Boolean>
    val intPreference: StateFlow<Int>
    val preferenceErrors: SharedFlow<Throwable> // For error reporting

    suspend fun updateStringPreference(newValue: String)
    suspend fun updateBooleanPreference(newValue: Boolean)
    suspend fun updateIntPreference(newValue: Int)
    suspend fun clear()
}
