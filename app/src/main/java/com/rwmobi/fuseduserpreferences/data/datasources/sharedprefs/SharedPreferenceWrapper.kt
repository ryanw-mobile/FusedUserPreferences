/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.sharedprefs

import android.content.SharedPreferences
import com.rwmobi.fuseduserpreferences.data.datasources.AppPreferences
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_BOOLEAN
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_INT
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_STRING
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedPreferenceWrapper(
    private val sharedPreferences: SharedPreferences,
    private val stringPreferenceDefault: String = "",
    private val booleanPreferenceDefault: Boolean = false,
    private val intPreferenceDefault: Int = 0,
) : AppPreferences {

    private val _stringPreference = MutableStateFlow(stringPreferenceDefault)
    override val stringPreference = _stringPreference.asStateFlow()

    private val _booleanPreference = MutableStateFlow(booleanPreferenceDefault)
    override val booleanPreference = _booleanPreference.asStateFlow()

    private val _intPreference = MutableStateFlow(intPreferenceDefault)
    override val intPreference = _intPreference.asStateFlow()

    // SharedPreferences operations do not typically throw exceptions in their standard use
    private val _preferenceErrors = MutableSharedFlow<Throwable>()
    override val preferenceErrors = _preferenceErrors.asSharedFlow()

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPref, key ->
            when (key) {
                PREF_KEY_STRING -> {
                    _stringPreference.value = sharedPref.getString(PREF_KEY_STRING, null) ?: stringPreferenceDefault
                }

                PREF_KEY_BOOLEAN -> {
                    _booleanPreference.value = sharedPref.getBoolean(PREF_KEY_BOOLEAN, booleanPreferenceDefault)
                }

                PREF_KEY_INT -> {
                    _intPreference.value = sharedPref.getInt(PREF_KEY_INT, intPreferenceDefault)
                }
            }
        }
    }

    override suspend fun updateStringPreference(newValue: String) {
        try {
            sharedPreferences.edit()
                .putString(PREF_KEY_STRING, newValue)
                .apply()
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    override suspend fun updateBooleanPreference(newValue: Boolean) {
        try {
            sharedPreferences.edit()
                .putBoolean(PREF_KEY_BOOLEAN, newValue)
                .apply()
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    override suspend fun updateIntPreference(newValue: Int) {
        try {
            sharedPreferences.edit()
                .putInt(PREF_KEY_INT, newValue)
                .apply()
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    override suspend fun clear() {
        try {
            sharedPreferences.edit()
                .clear()
                .apply()
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }
}
