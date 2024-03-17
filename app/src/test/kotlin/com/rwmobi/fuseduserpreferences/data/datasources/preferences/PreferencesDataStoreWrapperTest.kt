/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.data.datasources.preferences

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
class PreferencesDataStoreWrapperTest {
    // Reference: https://medium.com/androiddevelopers/datastore-and-testing-edf7ae8df3d8
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TEST_DATASTORE_NAME)
    private lateinit var testCoroutineScope: CoroutineScope
    private lateinit var preferencesDataStoreWrapper: PreferencesDataStoreWrapper

    @Before
    fun setup() {
        val testContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        testCoroutineScope = TestScope()
        preferencesDataStoreWrapper = PreferencesDataStoreWrapper(
            dataStore = testContext.dataStore,
            externalCoroutineScope = testCoroutineScope,
        )
    }

    @Test
    fun testUpdateStringPreference() {
        testCoroutineScope.launch {
            preferencesDataStoreWrapper.updateStringPreference("Test String")
            assert(preferencesDataStoreWrapper.stringPreference.value == "Test String")
        }
    }

    @Test
    fun testUpdateBooleanPreference() {
        testCoroutineScope.launch {
            preferencesDataStoreWrapper.updateBooleanPreference(true)
            assert(preferencesDataStoreWrapper.booleanPreference.value)
        }
    }

    @Test
    fun testUpdateIntPreference() {
        testCoroutineScope.launch {
            preferencesDataStoreWrapper.updateIntPreference(42)
            assert(preferencesDataStoreWrapper.intPreference.value == 42)
        }
    }

    @Test
    fun testClear() {
        testCoroutineScope.launch {
            preferencesDataStoreWrapper.updateStringPreference("Test String")
            preferencesDataStoreWrapper.updateBooleanPreference(true)
            preferencesDataStoreWrapper.updateIntPreference(42)

            preferencesDataStoreWrapper.clear()

            assert(preferencesDataStoreWrapper.stringPreference.value == "")
            assert(preferencesDataStoreWrapper.booleanPreference.value == false)
            assert(preferencesDataStoreWrapper.intPreference.value == 0)
        }
    }
}
