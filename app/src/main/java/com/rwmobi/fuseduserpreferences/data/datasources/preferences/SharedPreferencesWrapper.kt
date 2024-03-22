/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.preferences

import android.content.SharedPreferences

class SharedPreferencesWrapper(
    private val sharedPreferences: SharedPreferences,
) {
    private var onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    // For simplicity we limit to one listener at a time
    fun registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener) {
        this.onSharedPreferenceChangeListener?.let {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(it)
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
        this.onSharedPreferenceChangeListener = onSharedPreferenceChangeListener
    }

    fun unregisterOnSharedPreferenceChangeListener() {
        this.onSharedPreferenceChangeListener?.let {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(it)
        }
        this.onSharedPreferenceChangeListener = null
    }

    fun getStringPreference(key: String, defaultValue: String) = sharedPreferences.getString(key, null) ?: defaultValue
    fun getBooleanPreference(key: String, defaultValue: Boolean) = sharedPreferences.getBoolean(key, defaultValue)
    fun getIntPreference(key: String, defaultValue: Int) = sharedPreferences.getInt(key, defaultValue)

    fun updateStringPreference(key: String, newValue: String) {
        sharedPreferences.edit()
            .putString(key, newValue)
            .apply()
    }

    fun updateBooleanPreference(key: String, newValue: Boolean) {
        sharedPreferences.edit()
            .putBoolean(key, newValue)
            .apply()
    }

    fun updateIntPreference(key: String, newValue: Int) {
        sharedPreferences.edit()
            .putInt(key, newValue)
            .apply()
    }

    fun clear() {
        val existingKeys = sharedPreferences.all.keys
        sharedPreferences.edit()
            .clear()
            .apply()

        for (key in existingKeys) {
            onSharedPreferenceChangeListener?.onSharedPreferenceChanged(sharedPreferences, key)
        }
    }
}
