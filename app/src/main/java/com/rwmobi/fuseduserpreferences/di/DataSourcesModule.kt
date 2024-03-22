/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.di

import androidx.datastore.core.DataStore
import com.rwmobi.fuseduserpreferences.data.datasources.preferences.PreferencesDataStoreWrapper
import com.rwmobi.fuseduserpreferences.data.datasources.preferences.SharedPreferencesWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourcesModule {
    @Provides
    @Singleton
    fun providePreferencesDataStore(
        dataStore: DataStore<androidx.datastore.preferences.core.Preferences>,
        @DispatcherModule.IoDispatcher dispatcher: CoroutineDispatcher,
    ): PreferencesDataStoreWrapper {
        return PreferencesDataStoreWrapper(
            dataStore = dataStore,
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
