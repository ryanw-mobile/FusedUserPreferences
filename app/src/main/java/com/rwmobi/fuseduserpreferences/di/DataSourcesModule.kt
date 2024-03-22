/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rwmobi.fuseduserpreferences.data.datasources.preferences.PreferencesDataStoreWrapper
import com.rwmobi.fuseduserpreferences.data.datasources.preferences.SharedPreferencesWrapper
import com.rwmobi.fuseduserpreferences.domain.DefaultValues
import com.rwmobi.fuseduserpreferences.domain.PreferenceKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourcesModule {
    @Provides
    @Singleton
    fun providePreferencesDataStore(
        dataStore: DataStore<androidx.datastore.preferences.core.Preferences>,
        externalCoroutineScope: CoroutineScope,
        @DispatcherModule.IoDispatcher dispatcher: CoroutineDispatcher,
    ): PreferencesDataStoreWrapper {
        return PreferencesDataStoreWrapper(
            dataStore = dataStore,
            prefKeyString = stringPreferencesKey(PreferenceKeys.PREF_KEY_STRING),
            prefKeyBoolean = booleanPreferencesKey(PreferenceKeys.PREF_KEY_BOOLEAN),
            prefKeyInt = intPreferencesKey(PreferenceKeys.PREF_KEY_INT),
            stringPreferenceDefault = DefaultValues.STRING_PREFERENCE,
            booleanPreferenceDefault = DefaultValues.BOOLEAN_PREFERENCE,
            intPreferenceDefault = DefaultValues.INT_PREFERENCE,
            externalCoroutineScope = externalCoroutineScope,
            dispatcher = dispatcher,
        )
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        sharedPreferences: android.content.SharedPreferences,
    ): SharedPreferencesWrapper {
        return SharedPreferencesWrapper(sharedPreferences = sharedPreferences)
    }
}
