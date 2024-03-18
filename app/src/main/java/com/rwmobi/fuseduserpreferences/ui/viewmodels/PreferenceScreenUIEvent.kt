/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui.viewmodels

data class PreferenceScreenUIEvent(
    val onErrorShown: (errorId: Long) -> Unit,
    val onUpdateStringPreference: (newValue: String) -> Unit,
    val onUpdateBooleanPreference: (newValue: Boolean) -> Unit,
    val onUpdateIntPreference: (newValue: Int) -> Unit,
    val onClearPreference: () -> Unit,
)
