/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui

sealed class BottomNavItem(var title: String, var screen_route: String) {

    object SharedPreferences : BottomNavItem("SharedPreferences", "sharedPreferences")
    object PreferencesDataStore : BottomNavItem("PreferencesDataStore", "preferencesDataStore")

    companion object {
        val allItems: List<BottomNavItem>
            get() = listOf(SharedPreferences, PreferencesDataStore)
    }
}
