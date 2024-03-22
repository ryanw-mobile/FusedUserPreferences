/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.preferences

import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedPreferencesWrapper(
    private val sharedPreferences: SharedPreferences,
    private val prefKeyString: String,
    private val prefKeyBoolean: String,
    private val prefKeyInt: String,
    private val stringPreferenceDefault: String,
    private val booleanPreferenceDefault: Boolean,
    private val intPreferenceDefault: Int,
) {

    private val _stringPreference = MutableStateFlow(stringPreferenceDefault)
    val stringPreference = _stringPreference.asStateFlow()

    private val _booleanPreference = MutableStateFlow(booleanPreferenceDefault)
    val booleanPreference = _booleanPreference.asStateFlow()

    private val _intPreference = MutableStateFlow(intPreferenceDefault)
    val intPreference = _intPreference.asStateFlow()

    // SharedPreferences operations do not typically throw exceptions in their standard use
    private val _preferenceErrors = MutableSharedFlow<Throwable>()
    val preferenceErrors = _preferenceErrors.asSharedFlow()

    // The preference manager does not currently store a strong reference to the listener. You must store a strong reference to the listener, or it will be susceptible to garbage collection.
    // Note: This callback will not be triggered when preferences are cleared via Editor#clear(), unless targeting android.os.Build.VERSION_CODES#R on devices running OS versions Android R or later.
    private val onSharedPreferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPref, key ->
        when (key) {
            prefKeyString -> {
                _stringPreference.value = sharedPref.getString(prefKeyString, null) ?: stringPreferenceDefault
            }

            prefKeyBoolean -> {
                _booleanPreference.value = sharedPref.getBoolean(prefKeyBoolean, booleanPreferenceDefault)
            }

            prefKeyInt -> {
                _intPreference.value = sharedPref.getInt(prefKeyInt, intPreferenceDefault)
            }
        }
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)

        // fetch existing values
        _stringPreference.value = sharedPreferences.getString(prefKeyString, null) ?: stringPreferenceDefault
        _booleanPreference.value = sharedPreferences.getBoolean(prefKeyBoolean, booleanPreferenceDefault)
        _intPreference.value = sharedPreferences.getInt(prefKeyInt, intPreferenceDefault)
    }

    suspend fun updateStringPreference(newValue: String) {
        try {
            sharedPreferences.edit()
                .putString(prefKeyString, newValue)
                .apply()
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    suspend fun updateBooleanPreference(newValue: Boolean) {
        try {
            sharedPreferences.edit()
                .putBoolean(prefKeyBoolean, newValue)
                .apply()
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    suspend fun updateIntPreference(newValue: Int) {
        try {
            sharedPreferences.edit()
                .putInt(prefKeyInt, newValue)
                .apply()
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    suspend fun clear() {
        try {
            val existingKeys = sharedPreferences.all.keys
            sharedPreferences.edit()
                .clear()
                .apply()

            for (key in existingKeys) {
                onSharedPreferenceChangeListener.onSharedPreferenceChanged(sharedPreferences, key)
            }
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }
}
