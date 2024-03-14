/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources

import kotlinx.coroutines.flow.StateFlow

const val PREF_KEY_STRING = "keyString"
const val PREF_KEY_BOOLEAN = "keyBoolean"
const val PREF_KEY_INT = "keyInt"

interface Preferences {

    val stringPreference: StateFlow<String>
    val booleanPreference: StateFlow<Boolean>
    val intPreference: StateFlow<Int>

    suspend fun updateStringPreference(newValue: String)
    suspend fun updateBooleanPreference(newValue: Boolean)
    suspend fun updateIntPreference(newValue: Int)
    suspend fun clear()
}
