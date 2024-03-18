/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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

    private val stringPreferenceDefault: String = ""
    private val booleanPreferenceDefault: Boolean = false
    private val intPreferenceDefault: Int = 0

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPreferences = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
        sharedPreferencesWrapper = SharedPreferencesWrapper(
            sharedPreferences = sharedPreferences,
            prefKeyString = "keyString",
            prefKeyBoolean = "keyBoolean",
            prefKeyInt = "keyInt",
            stringPreferenceDefault = stringPreferenceDefault,
            booleanPreferenceDefault = booleanPreferenceDefault,
            intPreferenceDefault = intPreferenceDefault,
        )
    }

    @Test
    fun `verify string preference update`() = runTest {
        val newValue = "newString"
        sharedPreferencesWrapper.updateStringPreference(newValue)
        assertEquals(newValue, sharedPreferencesWrapper.stringPreference.value)
    }

    @Test
    fun `verify boolean preference update`() = runTest {
        val newValue = true
        sharedPreferencesWrapper.updateBooleanPreference(newValue)
        assertEquals(newValue, sharedPreferencesWrapper.booleanPreference.value)
    }

    @Test
    fun `verify int preference update`() = runTest {
        val newValue = 42
        sharedPreferencesWrapper.updateIntPreference(newValue)
        assertEquals(newValue, sharedPreferencesWrapper.intPreference.value)
    }

    @Test
    fun `verify preferences are cleared`() = runTest {
        with(sharedPreferencesWrapper) {
            updateStringPreference("string")
            updateBooleanPreference(true)
            updateIntPreference(1)
            clear()

            assertEquals(stringPreferenceDefault, stringPreference.value)
            assertEquals(booleanPreferenceDefault, booleanPreference.value)
            assertEquals(intPreferenceDefault, intPreference.value)
        }
    }
}
