/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_DATASTORE_NAME: String = "test_datastore"

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PreferencesDataStoreWrapperTest {
    // Reference: https://medium.com/androiddevelopers/datastore-and-testing-edf7ae8df3d8
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TEST_DATASTORE_NAME)
    private lateinit var testCoroutineScope: CoroutineScope
    private lateinit var preferencesDataStoreWrapper: PreferencesDataStoreWrapper

    private val stringPreferenceDefault: String = ""
    private val booleanPreferenceDefault: Boolean = false
    private val intPreferenceDefault: Int = 0

    @Before
    fun setup() {
        val testContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        testCoroutineScope = TestScope()
        preferencesDataStoreWrapper = PreferencesDataStoreWrapper(
            dataStore = testContext.dataStore,
            externalCoroutineScope = testCoroutineScope,
            prefKeyString = stringPreferencesKey("keyString"),
            prefKeyBoolean = booleanPreferencesKey("keyBoolean"),
            prefKeyInt = intPreferencesKey("keyInt"),
            stringPreferenceDefault = stringPreferenceDefault,
            booleanPreferenceDefault = booleanPreferenceDefault,
            intPreferenceDefault = intPreferenceDefault,
        )
    }

    @Test
    fun testUpdateStringPreference() {
        testCoroutineScope.launch {
            val newValue = "newString"
            preferencesDataStoreWrapper.updateStringPreference(newValue)
            assertEquals(newValue, preferencesDataStoreWrapper.stringPreference.value)
        }
    }

    @Test
    fun testUpdateBooleanPreference() {
        testCoroutineScope.launch {
            val newValue = true
            preferencesDataStoreWrapper.updateBooleanPreference(newValue)
            assertEquals(newValue, preferencesDataStoreWrapper.booleanPreference.value)
        }
    }

    @Test
    fun testUpdateIntPreference() {
        testCoroutineScope.launch {
            val newValue = 42
            preferencesDataStoreWrapper.updateIntPreference(newValue)
            assertEquals(newValue, preferencesDataStoreWrapper.intPreference.value)
        }
    }

    @Test
    fun testClear() {
        testCoroutineScope.launch {
            with(preferencesDataStoreWrapper) {
                updateStringPreference("Test String")
                updateBooleanPreference(true)
                updateIntPreference(42)

                clear()

                assertEquals(stringPreferenceDefault, stringPreference.value)
                assertEquals(booleanPreferenceDefault, booleanPreference.value)
                assertEquals(intPreferenceDefault, intPreference.value)
            }
        }
    }
}
