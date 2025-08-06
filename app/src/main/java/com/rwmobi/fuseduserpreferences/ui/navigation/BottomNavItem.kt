/*
 * Copyright (c) 2025. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui.navigation

sealed class BottomNavItem(var title: String, var screenRoute: String) {

    data object SharedPreferences : BottomNavItem("SharedPreferences", "sharedPreferences")
    data object PreferencesDataStore : BottomNavItem("PreferencesDataStore", "preferencesDataStore")

    companion object {
        val allItems: List<BottomNavItem>
            get() = listOf(SharedPreferences, PreferencesDataStore)
    }
}
