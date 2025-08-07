/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.repositories

import android.content.SharedPreferences
import com.rwmobi.fuseduserpreferences.data.datasources.preferences.SharedPreferencesWrapper
import com.rwmobi.fuseduserpreferences.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

@Suppress("TooGenericExceptionCaught")
class SharedPreferencesRepository(
    private val sharedPreferencesWrapper: SharedPreferencesWrapper,
    private val prefKeyString: String,
    private val prefKeyBoolean: String,
    private val prefKeyInt: String,
    private val stringPreferenceDefault: String,
    private val booleanPreferenceDefault: Boolean,
    private val intPreferenceDefault: Int,
) : UserPreferencesRepository {
    private val _stringPreference = MutableStateFlow(stringPreferenceDefault)
    override val stringPreference = _stringPreference

    private val _booleanPreference = MutableStateFlow(booleanPreferenceDefault)
    override val booleanPreference = _booleanPreference

    private val _intPreference = MutableStateFlow(intPreferenceDefault)
    override val intPreference = _intPreference

    // SharedPreferences operations do not typically throw exceptions in their standard use
    private val _preferenceErrors = MutableSharedFlow<Throwable>()
    override val preferenceErrors = _preferenceErrors

    // The preference manager does not currently store a strong reference to the listener.
    // You must store a strong reference to the listener, or it will be susceptible to garbage collection.
    // Note: This callback will not be triggered when preferences are cleared via Editor#clear(),
    // unless targeting android.os.Build.VERSION_CODES#R on devices running OS versions Android R or later.
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
        sharedPreferencesWrapper.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)

        // fetch existing values
        _stringPreference.value = sharedPreferencesWrapper.getStringPreference(key = prefKeyString, defaultValue = stringPreferenceDefault)
        _booleanPreference.value = sharedPreferencesWrapper.getBooleanPreference(prefKeyBoolean, booleanPreferenceDefault)
        _intPreference.value = sharedPreferencesWrapper.getIntPreference(prefKeyInt, intPreferenceDefault)
    }

    override suspend fun updateStringPreference(newValue: String) {
        try {
            sharedPreferencesWrapper.updateStringPreference(prefKeyString, newValue)
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    override suspend fun updateBooleanPreference(newValue: Boolean) {
        try {
            sharedPreferencesWrapper.updateBooleanPreference(prefKeyBoolean, newValue)
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    override suspend fun updateIntPreference(newValue: Int) {
        try {
            sharedPreferencesWrapper.updateIntPreference(prefKeyInt, newValue)
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }

    override suspend fun clear() {
        try {
            sharedPreferencesWrapper.clear()
        } catch (e: Throwable) {
            _preferenceErrors.emit(e)
        }
    }
}
