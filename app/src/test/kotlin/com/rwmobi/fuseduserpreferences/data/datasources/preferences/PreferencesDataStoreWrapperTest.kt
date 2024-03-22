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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_DATASTORE_NAME: String = "test_datastore"

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PreferencesDataStoreWrapperTest {
    // Reference: https://medium.com/androiddevelopers/datastore-and-testing-edf7ae8df3d8
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TEST_DATASTORE_NAME)
    private lateinit var preferencesDataStoreWrapper: PreferencesDataStoreWrapper

    private val prefKeyString = stringPreferencesKey("keyString")
    private val prefKeyBoolean = booleanPreferencesKey("keyBoolean")
    private val prefKeyInt = intPreferencesKey("keyInt")

    private val stringPreferenceDefault: String = ""
    private val booleanPreferenceDefault: Boolean = false
    private val intPreferenceDefault: Int = 0

    @Before
    fun setup() {
        val testContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        preferencesDataStoreWrapper = PreferencesDataStoreWrapper(
            dataStore = testContext.dataStore,
            dispatcher = Dispatchers.Unconfined,
        )
    }

    @Test
    fun testUpdateStringPreference() = runTest {
        val newValue = "newString"
        // Start collecting the flow in a separate coroutine to observe updates.
        val dataFlow = preferencesDataStoreWrapper.getDataStoreFlow()

        // Update the preference and wait for the flow to collect the new value.
        preferencesDataStoreWrapper.updateStringPreference(key = prefKeyString, newValue = newValue)

        val flowJob = launch {
            dataFlow.collect { preferences ->
                if (preferences[prefKeyString] == newValue) {
                    assertEquals(newValue, preferences[prefKeyString])
                    cancel() // Cancel the job once we've observed the update
                }
            }
        }
        flowJob.join() // Wait for the job to complete
    }

    @Test
    fun testUpdateBooleanPreference() = runTest {
        val newValue = true
        val dataFlow = preferencesDataStoreWrapper.getDataStoreFlow()

        preferencesDataStoreWrapper.updateBooleanPreference(key = prefKeyBoolean, newValue = newValue)

        val flowJob = launch {
            dataFlow.collect { preferences ->
                if (preferences[prefKeyBoolean] == newValue) {
                    assertEquals(newValue, preferences[prefKeyBoolean])
                    cancel()
                }
            }
        }
        flowJob.join()
    }

    @Test
    fun testUpdateIntPreference() = runTest {
        val newValue = 42
        val dataFlow = preferencesDataStoreWrapper.getDataStoreFlow()

        preferencesDataStoreWrapper.updateIntPreference(key = prefKeyInt, newValue = newValue)

        val flowJob = launch {
            dataFlow.collect { preferences ->
                if (preferences[prefKeyInt] == newValue) {
                    assertEquals(newValue, preferences[prefKeyInt])
                    cancel() // Cancel the job once we've observed the update
                }
            }
        }
        flowJob.join()
    }

    @Test
    fun testClear() = runTest {
        // Setup initial values
        with(preferencesDataStoreWrapper) {
            updateStringPreference(key = prefKeyString, newValue = "newString")
            updateBooleanPreference(key = prefKeyBoolean, newValue = true)
            updateIntPreference(key = prefKeyInt, newValue = 42)
        }

        val dataFlow = preferencesDataStoreWrapper.getDataStoreFlow()

        preferencesDataStoreWrapper.clear()

        val flowJob = launch {
            dataFlow.collect { preferences ->
                if (preferences[prefKeyString] == null && preferences[prefKeyBoolean] == null && preferences[prefKeyInt] == null) {
                    assertEquals(null, preferences[prefKeyString])
                    assertEquals(null, preferences[prefKeyBoolean])
                    assertEquals(null, preferences[prefKeyInt])
                    cancel()
                }
            }
        }

        flowJob.join()
    }
}
