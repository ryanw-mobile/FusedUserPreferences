/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package com.rwmobi.fuseduserpreferences.di

import com.rwmobi.fuseduserpreferences.data.datasources.preferences.Preferences
import com.rwmobi.fuseduserpreferences.data.repositories.UserPreferencesRepositoryImpl
import com.rwmobi.fuseduserpreferences.domain.repositories.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
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
        @PreferencesDataStore preferences: Preferences,
        @DispatcherModule.IoDispatcher dispatcher: CoroutineDispatcher,
    ): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(
            preferences = preferences,
            dispatcher = dispatcher,
        )
    }

    @SharedPreferences
    @Provides
    @ViewModelScoped
    fun provideSharedPreferencesRepository(
        @SharedPreferences preferences: Preferences,
        @DispatcherModule.IoDispatcher dispatcher: CoroutineDispatcher,
    ): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(
            preferences = preferences,
            dispatcher = dispatcher,
        )
    }
}
