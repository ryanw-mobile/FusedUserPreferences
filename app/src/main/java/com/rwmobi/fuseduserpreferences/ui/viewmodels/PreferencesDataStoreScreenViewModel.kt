/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rwmobi.fuseduserpreferences.di.DispatcherModule
import com.rwmobi.fuseduserpreferences.di.PreferencesDataStore
import com.rwmobi.fuseduserpreferences.domain.repositories.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PreferencesDataStoreScreenViewModel @Inject constructor(
    @PreferencesDataStore private val userPreferencesRepository: UserPreferencesRepository,
    @DispatcherModule.MainDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PreferenceScreenUIState())
    var uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            userPreferencesRepository.stringPreference.collect { stringPreference ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        stringPreference = stringPreference,
                        isLoading = setOf(stringPreference, currentUiState.booleanPreference, currentUiState.intPreference).contains(null),
                    )
                }
            }
        }

        viewModelScope.launch(dispatcher) {
            userPreferencesRepository.booleanPreference.collect { booleanPreference ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        booleanPreference = booleanPreference,
                        isLoading = setOf(booleanPreference, currentUiState.stringPreference, currentUiState.intPreference).contains(null),
                    )
                }
            }
        }

        viewModelScope.launch(dispatcher) {
            userPreferencesRepository.intPreference.collect { intPreference ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        intPreference = intPreference,
                        isLoading = setOf(intPreference, currentUiState.booleanPreference, currentUiState.stringPreference).contains(null),
                    )
                }
            }
        }

        viewModelScope.launch(dispatcher) {
            userPreferencesRepository.preferenceErrors.collect { preferenceErrors ->
                Timber.e(preferenceErrors)

                _uiState.update { currentUiState ->
                    val errorMessages = currentUiState.errorMessages + ErrorMessage(
                        id = UUID.randomUUID().mostSignificantBits,
                        message = preferenceErrors.localizedMessage ?: "unknown error",
                    )
                    currentUiState.copy(
                        isLoading = false,
                        errorMessages = errorMessages,
                    )
                }
            }
        }
    }

    fun errorShown(errorId: Long) {
        _uiState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }

    fun updateStringPreference(newValue: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateStringPreference(newValue)
        }
    }

    fun updateBooleanPreference(newValue: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateBooleanPreference(newValue)
        }
    }

    fun updateIntPreference(newValue: Int) {
        viewModelScope.launch {
            userPreferencesRepository.updateIntPreference(newValue)
        }
    }

    fun clearPreferences() {
        viewModelScope.launch {
            userPreferencesRepository.clear()
        }
    }
}
