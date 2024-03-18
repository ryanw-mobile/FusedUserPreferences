/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.rwmobi.fuseduserpreferences.domain.DefaultValues
import com.rwmobi.fuseduserpreferences.ui.screens.PreferenceScreen
import com.rwmobi.fuseduserpreferences.ui.theme.FusedUserPreferencesTheme
import com.rwmobi.fuseduserpreferences.ui.viewmodels.PreferenceScreenUIEvent
import com.rwmobi.fuseduserpreferences.ui.viewmodels.SharedPreferenceScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val viewModel: SharedPreferenceScreenViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            FusedUserPreferencesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    PreferenceScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 16.dp),
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
    }
}
