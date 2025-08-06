/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package com.rwmobi.fuseduserpreferences.di

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rwmobi.fuseduserpreferences.data.datasources.preferences.PreferencesDataStoreWrapper
import com.rwmobi.fuseduserpreferences.data.datasources.preferences.SharedPreferencesWrapper
import com.rwmobi.fuseduserpreferences.data.repositories.PreferencesDataStoreRepository
import com.rwmobi.fuseduserpreferences.data.repositories.SharedPreferencesRepository
import com.rwmobi.fuseduserpreferences.domain.DefaultValues
import com.rwmobi.fuseduserpreferences.domain.PreferenceKeys
import com.rwmobi.fuseduserpreferences.domain.repositories.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PreferencesDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SharedPreferences

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @PreferencesDataStore
    @Provides
    @ViewModelScoped
    fun providePreferencesDataStoreRepository(
        preferenceDataStoreWrapper: PreferencesDataStoreWrapper,
        externalCoroutineScope: CoroutineScope,
        @DispatcherModule.IoDispatcher dispatcher: CoroutineDispatcher,
    ): UserPreferencesRepository = PreferencesDataStoreRepository(
        preferenceDataStoreWrapper = preferenceDataStoreWrapper,
        prefKeyString = stringPreferencesKey(PreferenceKeys.PREF_KEY_STRING),
        prefKeyBoolean = booleanPreferencesKey(PreferenceKeys.PREF_KEY_BOOLEAN),
        prefKeyInt = intPreferencesKey(PreferenceKeys.PREF_KEY_INT),
        stringPreferenceDefault = DefaultValues.STRING_PREFERENCE,
        booleanPreferenceDefault = DefaultValues.BOOLEAN_PREFERENCE,
        intPreferenceDefault = DefaultValues.INT_PREFERENCE,
        externalCoroutineScope = externalCoroutineScope,
        dispatcher = dispatcher,
    )

    @SharedPreferences
    @Provides
    @ViewModelScoped
    fun provideSharedPreferencesRepository(
        sharedPreferencesWrapper: SharedPreferencesWrapper,
    ): UserPreferencesRepository = SharedPreferencesRepository(
        sharedPreferencesWrapper,
        prefKeyString = PreferenceKeys.PREF_KEY_STRING,
        prefKeyBoolean = PreferenceKeys.PREF_KEY_BOOLEAN,
        prefKeyInt = PreferenceKeys.PREF_KEY_INT,
        stringPreferenceDefault = DefaultValues.STRING_PREFERENCE,
        booleanPreferenceDefault = DefaultValues.BOOLEAN_PREFERENCE,
        intPreferenceDefault = DefaultValues.INT_PREFERENCE,
    )
}
