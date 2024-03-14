/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.sharedprefs

import android.content.SharedPreferences
import com.rwmobi.fuseduserpreferences.data.datasources.AppPreferences
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_BOOLEAN
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_INT
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_STRING
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedPreferenceWrapper(private val sharedPreferences: SharedPreferences) : AppPreferences {

    private val _stringPreference = MutableStateFlow("")
    override val stringPreference = _stringPreference.asStateFlow()

    private val _booleanPreference = MutableStateFlow(false)
    override val booleanPreference = _booleanPreference.asStateFlow()

    private val _intPreference = MutableStateFlow(0)
    override val intPreference = _intPreference.asStateFlow()

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPref, key ->
            when (key) {
                PREF_KEY_STRING -> {
                    _stringPreference.value = sharedPref.getString(PREF_KEY_STRING, null) ?: ""
                }

                PREF_KEY_BOOLEAN -> {
                    _booleanPreference.value = sharedPref.getBoolean(PREF_KEY_BOOLEAN, false)
                }

                PREF_KEY_INT -> {
                    _intPreference.value = sharedPref.getInt(PREF_KEY_INT, 0)
                }
            }
        }
    }

    override suspend fun updateStringPreference(newValue: String) {
        sharedPreferences.edit()
            .putString(PREF_KEY_STRING, newValue)
            .apply()
    }

    override suspend fun updateBooleanPreference(newValue: Boolean) {
        sharedPreferences.edit()
            .putBoolean(PREF_KEY_BOOLEAN, newValue)
            .apply()
    }

    override suspend fun updateIntPreference(newValue: Int) {
        sharedPreferences.edit()
            .putInt(PREF_KEY_INT, newValue)
            .apply()
    }

    override suspend fun clear() {
        sharedPreferences.edit()
            .clear()
            .apply()
    }
}
