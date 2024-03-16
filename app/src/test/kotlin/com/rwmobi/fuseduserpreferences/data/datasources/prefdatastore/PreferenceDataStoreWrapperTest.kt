/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.prefdatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
class PreferenceDataStoreWrapperTest {
    // Reference: https://medium.com/androiddevelopers/datastore-and-testing-edf7ae8df3d8
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TEST_DATASTORE_NAME)
    private lateinit var testCoroutineScope: CoroutineScope
    private lateinit var preferenceDataStoreWrapper: PreferenceDataStoreWrapper

    @Before
    fun setup() {
        val testContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        testCoroutineScope = TestScope()
        preferenceDataStoreWrapper = PreferenceDataStoreWrapper(dataStore = testContext.dataStore, externalCoroutineScope = testCoroutineScope)
    }

    @Test
    fun testUpdateStringPreference() {
        testCoroutineScope.launch {
            preferenceDataStoreWrapper.updateStringPreference("Test String")
            assert(preferenceDataStoreWrapper.stringPreference.value == "Test String")
        }
    }

    @Test
    fun testUpdateBooleanPreference() {
        testCoroutineScope.launch {
            preferenceDataStoreWrapper.updateBooleanPreference(true)
            assert(preferenceDataStoreWrapper.booleanPreference.value)
        }
    }

    @Test
    fun testUpdateIntPreference() {
        testCoroutineScope.launch {
            preferenceDataStoreWrapper.updateIntPreference(42)
            assert(preferenceDataStoreWrapper.intPreference.value == 42)
        }
    }

    @Test
    fun testClear() {
        testCoroutineScope.launch {
            preferenceDataStoreWrapper.updateStringPreference("Test String")
            preferenceDataStoreWrapper.updateBooleanPreference(true)
            preferenceDataStoreWrapper.updateIntPreference(42)

            preferenceDataStoreWrapper.clear()

            assert(preferenceDataStoreWrapper.stringPreference.value == "")
            assert(preferenceDataStoreWrapper.booleanPreference.value == false)
            assert(preferenceDataStoreWrapper.intPreference.value == 0)
        }
    }
}
