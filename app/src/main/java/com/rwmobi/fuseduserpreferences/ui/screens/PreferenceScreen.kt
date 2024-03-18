/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.rwmobi.fuseduserpreferences.domain.DefaultValues
import com.rwmobi.fuseduserpreferences.ui.components.BooleanSwitch
import com.rwmobi.fuseduserpreferences.ui.components.IntegerSlider
import com.rwmobi.fuseduserpreferences.ui.components.StringTextField
import com.rwmobi.fuseduserpreferences.ui.theme.FusedUserPreferencesTheme
import com.rwmobi.fuseduserpreferences.ui.viewmodels.PreferenceScreenUIEvent
import com.rwmobi.fuseduserpreferences.ui.viewmodels.PreferenceScreenUIState

@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    uiState: PreferenceScreenUIState,
    uiEvent: PreferenceScreenUIEvent,
    stringPreferenceDefault: String,
    booleanPreferenceDefault: Boolean,
    intPreferenceDefault: Int,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .verticalScroll(
                    state = rememberScrollState(),
                    enabled = true,
                )
                .padding(all = 32.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            StringTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Stored value = ${uiState.stringPreference}",
                value = uiState.stringPreference ?: stringPreferenceDefault,
                onValueChange = uiEvent.onUpdateStringPreference,
            )

            BooleanSwitch(
                modifier = Modifier.fillMaxWidth(),
                label = "Stored value = ${uiState.booleanPreference}",
                checked = uiState.booleanPreference ?: booleanPreferenceDefault,
                onCheckedChange = uiEvent.onUpdateBooleanPreference,
            )

            IntegerSlider(
                modifier = Modifier.fillMaxWidth(),
                label = "Stored value = ${uiState.intPreference}",
                sliderPosition = (uiState.intPreference ?: intPreferenceDefault) / 100.0f,
                onSliderValueChange = uiEvent.onUpdateIntPreference,
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = uiEvent.onClearPreference,
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium,
                    text = "Clear",
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }

    if (uiState.errorMessages.isNotEmpty()) {
        val errorMessage = remember(uiState) { uiState.errorMessages[0] }
        val errorMessageText = errorMessage.message
        val actionLabel = stringResource(android.R.string.ok)

        LaunchedEffect(errorMessage.id) {
            snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = actionLabel,
            )
            uiEvent.onErrorShown(errorMessage.id)
        }
    }
}

@PreviewLightDark
@PreviewDynamicColors
@PreviewFontScale
@PreviewScreenSizes
@Composable
private fun PreferenceScreenPreview(
    @PreviewParameter(LoremIpsum::class) text: String,
) {
    FusedUserPreferencesTheme {
        PreferenceScreen(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            uiState = PreferenceScreenUIState(
                isLoading = false,
                stringPreference = text,
                booleanPreference = true,
                intPreference = 50,
            ),
            uiEvent = PreferenceScreenUIEvent(
                onErrorShown = {},
                onUpdateStringPreference = {},
                onUpdateBooleanPreference = {},
                onUpdateIntPreference = {},
                onClearPreference = {},
            ),
            stringPreferenceDefault = DefaultValues.STRING_PREFERENCE,
            booleanPreferenceDefault = DefaultValues.BOOLEAN_PREFERENCE,
            intPreferenceDefault = DefaultValues.INT_PREFERENCE,
        )
    }
}
