/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui.viewmodels

data class PreferenceScreenUIState(
    val isLoading: Boolean = true,
    val stringPreference: String? = null,
    val booleanPreference: Boolean? = null,
    val intPreference: Int? = null,
    val errorMessages: List<ErrorMessage> = emptyList(),
)
