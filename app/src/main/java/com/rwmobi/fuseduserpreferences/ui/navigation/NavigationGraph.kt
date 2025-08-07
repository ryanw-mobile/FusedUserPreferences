/*
 * Copyright (c) 2024-2025. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rwmobi.fuseduserpreferences.domain.DefaultValues
import com.rwmobi.fuseduserpreferences.ui.screens.PreferenceScreen
import com.rwmobi.fuseduserpreferences.ui.viewmodels.PreferenceScreenUIEvent
import com.rwmobi.fuseduserpreferences.ui.viewmodels.PreferencesDataStoreScreenViewModel
import com.rwmobi.fuseduserpreferences.ui.viewmodels.SharedPreferenceScreenViewModel

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = "sharedPreferences") {
        composable(route = "sharedPreferences") {
            val viewModel: SharedPreferenceScreenViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            PreferenceScreen(
                modifier = modifier,
                uiState = uiState,
                uiEvent = PreferenceScreenUIEvent(
                    onErrorShown = { viewModel.errorShown(it) },
                    onUpdateStringPreference = { viewModel.updateStringPreference(it) },
                    onUpdateBooleanPreference = { viewModel.updateBooleanPreference(it) },
                    onUpdateIntPreference = { viewModel.updateIntPreference(it) },
                    onClearPreference = { viewModel.clearPreferences() },
                ),
                stringPreferenceDefault = DefaultValues.STRING_PREFERENCE,
                booleanPreferenceDefault = DefaultValues.BOOLEAN_PREFERENCE,
                intPreferenceDefault = DefaultValues.INT_PREFERENCE,
            )
        }
        composable(route = "preferencesDataStore") {
            val viewModel: PreferencesDataStoreScreenViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            PreferenceScreen(
                modifier = modifier,
                uiState = uiState,
                uiEvent = PreferenceScreenUIEvent(
                    onErrorShown = { viewModel.errorShown(it) },
                    onUpdateStringPreference = { viewModel.updateStringPreference(it) },
                    onUpdateBooleanPreference = { viewModel.updateBooleanPreference(it) },
                    onUpdateIntPreference = { viewModel.updateIntPreference(it) },
                    onClearPreference = { viewModel.clearPreferences() },
                ),
                stringPreferenceDefault = DefaultValues.STRING_PREFERENCE,
                booleanPreferenceDefault = DefaultValues.BOOLEAN_PREFERENCE,
                intPreferenceDefault = DefaultValues.INT_PREFERENCE,
            )
        }
    }
}
