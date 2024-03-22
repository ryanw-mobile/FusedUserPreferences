/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class SharedPreferencesWrapperTest {

    private lateinit var sharedPreferencesWrapper: SharedPreferencesWrapper
    private lateinit var sharedPreferences: SharedPreferences

    private val prefKeyString: String = "keyString"
    private val prefKeyBoolean: String = "keyBoolean"
    private val prefKeyInt: String = "keyInt"

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPreferences = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
        sharedPreferencesWrapper = SharedPreferencesWrapper(
            sharedPreferences = sharedPreferences,
        )
    }

    @Test
    fun `verify string preference update`() {
        val newValue = "newString"
        var callbackValue: String? = null
        sharedPreferencesWrapper.registerOnSharedPreferenceChangeListener { sharedPref, key ->
            if (key == prefKeyString) {
                callbackValue = sharedPref.getString(prefKeyString, null)
            }
        }

        sharedPreferencesWrapper.updateStringPreference(key = prefKeyString, newValue = newValue)

        assertEquals(newValue, sharedPreferencesWrapper.getStringPreference(key = prefKeyString, defaultValue = "some-invalid-value"))
        assertEquals(newValue, callbackValue)
    }

    @Test
    fun `verify boolean preference update`() {
        val newValue = true
        var callbackValue: Boolean? = null
        sharedPreferencesWrapper.registerOnSharedPreferenceChangeListener { sharedPref, key ->
            if (key == prefKeyBoolean) {
                callbackValue = sharedPref.getBoolean(prefKeyBoolean, !newValue)
            }
        }

        sharedPreferencesWrapper.updateBooleanPreference(key = prefKeyBoolean, newValue = newValue)

        assertEquals(newValue, sharedPreferencesWrapper.getBooleanPreference(key = prefKeyBoolean, defaultValue = !newValue))
        assertEquals(newValue, callbackValue)
    }

    @Test
    fun `verify int preference update`() {
        val newValue = 42
        var callbackValue: Int? = null
        sharedPreferencesWrapper.registerOnSharedPreferenceChangeListener { sharedPref, key ->
            if (key == prefKeyInt) {
                callbackValue = sharedPref.getInt(prefKeyInt, -1)
            }
        }

        sharedPreferencesWrapper.updateIntPreference(key = prefKeyInt, newValue = newValue)

        assertEquals(newValue, sharedPreferencesWrapper.getIntPreference(key = prefKeyInt, defaultValue = -1))
        assertEquals(newValue, callbackValue)
    }

    @Test
    fun `verify preferences are cleared`() {
        with(sharedPreferencesWrapper) {
            var callbackValueString: String? = null
            var callbackValueBoolean: Boolean? = true
            var callbackValueInt: Int? = 1
            val expectedString = "expected-default-string"
            val expectedBoolean = false
            val expectedInt = -1
            registerOnSharedPreferenceChangeListener { sharedPref, key ->
                when (key) {
                    prefKeyString -> {
                        callbackValueString = sharedPref.getString(prefKeyString, expectedString)
                    }

                    prefKeyBoolean -> {
                        callbackValueBoolean = sharedPref.getBoolean(prefKeyBoolean, expectedBoolean)
                    }

                    prefKeyInt -> {
                        callbackValueInt = sharedPref.getInt(prefKeyInt, expectedInt)
                    }
                }
            }

            updateStringPreference(key = prefKeyString, newValue = "some-invalid-value")
            updateBooleanPreference(key = prefKeyBoolean, newValue = true)
            updateIntPreference(key = prefKeyInt, newValue = 1)
            clear()

            assertEquals(expectedString, sharedPreferencesWrapper.getStringPreference(key = prefKeyString, defaultValue = expectedString))
            assertEquals(expectedString, callbackValueString)
            assertEquals(expectedBoolean, sharedPreferencesWrapper.getBooleanPreference(key = prefKeyBoolean, defaultValue = expectedBoolean))
            assertEquals(expectedBoolean, callbackValueBoolean)
            assertEquals(expectedInt, sharedPreferencesWrapper.getIntPreference(key = prefKeyInt, defaultValue = expectedInt))
            assertEquals(expectedInt, callbackValueInt)
        }
    }
}
