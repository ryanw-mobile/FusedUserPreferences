package com.rwmobi.fuseduserpreferences.data.datasources.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_BOOLEAN
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_INT
import com.rwmobi.fuseduserpreferences.data.datasources.PREF_KEY_STRING
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
class SharedPreferenceWrapperTest {

    private lateinit var sharedPreferenceWrapper: SharedPreferenceWrapper
    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPreferences = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
        sharedPreferenceWrapper = SharedPreferenceWrapper(sharedPreferences)
    }

    @Test
    fun `verify string preference update`() = runTest {
        val newValue = "newString"
        sharedPreferenceWrapper.updateStringPreference(newValue)

        assertEquals(newValue, sharedPreferences.getString(PREF_KEY_STRING, null))
    }

    @Test
    fun `verify boolean preference update`() = runTest {
        val newValue = true
        sharedPreferenceWrapper.updateBooleanPreference(newValue)

        assertEquals(newValue, sharedPreferences.getBoolean(PREF_KEY_BOOLEAN, false))
    }

    @Test
    fun `verify int preference update`() = runTest {
        val newValue = 42
        sharedPreferenceWrapper.updateIntPreference(newValue)

        assertEquals(newValue, sharedPreferences.getInt(PREF_KEY_INT, 0))
    }

    @Test
    fun `verify preferences are cleared`() = runTest {
        // Setup initial values
        with(sharedPreferences.edit()) {
            putString(PREF_KEY_STRING, "string")
            putBoolean(PREF_KEY_BOOLEAN, true)
            putInt(PREF_KEY_INT, 1)
            commit()
        }

        // Clear preferences
        sharedPreferenceWrapper.clear()

        with(sharedPreferences) {
            assertEquals("", getString(PREF_KEY_STRING, ""))
            assertEquals(false, getBoolean(PREF_KEY_BOOLEAN, false))
            assertEquals(0, getInt(PREF_KEY_INT, 0))
        }
    }
}
