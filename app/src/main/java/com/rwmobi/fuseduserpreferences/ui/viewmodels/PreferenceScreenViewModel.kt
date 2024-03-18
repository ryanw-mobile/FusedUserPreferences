/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rwmobi.fuseduserpreferences.di.DispatcherModule
import com.rwmobi.fuseduserpreferences.domain.repositories.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferenceScreenViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
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
            userPreferencesRepository.stringPreference.collect { stringPreference ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        stringPreference = stringPreference,
                        isLoading = setOf(stringPreference, currentUiState.booleanPreference, currentUiState.intPreference).contains(null),
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
}
