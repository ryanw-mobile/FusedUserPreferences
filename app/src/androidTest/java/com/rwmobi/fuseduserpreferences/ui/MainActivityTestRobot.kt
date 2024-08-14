/*
 * Copyright (c) 2024. Ryan Wong
 * https://github.com/ryanw-mobile
 */

package com.rwmobi.fuseduserpreferences.ui

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.rwmobi.fuseduserpreferences.ui.test.XLauncherIconsTestRule
import com.rwmobi.fuseduserpreferences.ui.test.withRole

internal class MainActivityTestRobot(
    private val composeTestRule: XLauncherIconsTestRule,
) {
    fun checkAppLayoutIsDisplayed() {
        try {
            assertNavigationItemsAreDisplayed()
        } catch (e: AssertionError) {
            composeTestRule.onRoot().printToLog("MainActivityTestRobotError")
            throw AssertionError("Expected App layout is not displayed. ${e.message}", e)
        }
    }

    fun navigateToSharedPreferencesScreen() {
        try {
            tapSharedPreferencesTab()
            assertSharedPreferencesTabIsSelected()
        } catch (e: AssertionError) {
            composeTestRule.onRoot().printToLog("MainActivityTestRobotError")
            throw AssertionError("Expected SharedPreferences screen layout is not displayed. ${e.message}", e)
        }
    }

    fun navigateToPreferencesDataStoreScreen() {
        try {
            tapPreferencesDataStoreTab()
            assertPreferencesDataStoreTabIsSelected()
        } catch (e: AssertionError) {
            composeTestRule.onRoot().printToLog("MainActivityTestRobotError")
            throw AssertionError("Expected PreferencesDataStore screen layout is not displayed. ${e.message}", e)
        }
    }

    // Actions
    fun printSemanticTree() {
        composeTestRule.onRoot().printToLog("SemanticTree")
    }

    private fun tapSharedPreferencesTab() {
        with(composeTestRule) {
            onNode(
                matcher = withRole(Role.Tab) and hasText(text = BottomNavItem.SharedPreferences.title),
            ).performClick()
        }
    }

    private fun tapPreferencesDataStoreTab() {
        with(composeTestRule) {
            onNode(
                matcher = withRole(Role.Tab) and hasText(text = BottomNavItem.PreferencesDataStore.title),
            ).performClick()
        }
    }

    // Assertions
    private fun assertNavigationItemsAreDisplayed() {
        with(composeTestRule) {
            for (navigationItem in BottomNavItem.allItems) {
                onNode(
                    matcher = withRole(Role.Tab) and hasText(text = navigationItem.title),
                ).assertIsDisplayed()
            }
        }
    }

    private fun assertSharedPreferencesTabIsSelected() {
        with(composeTestRule) {
            onNode(
                matcher = withRole(Role.Tab) and hasText(text = BottomNavItem.SharedPreferences.title),
            ).assertIsSelected()
        }
    }

    private fun assertPreferencesDataStoreTabIsSelected() {
        with(composeTestRule) {
            onNode(
                matcher = withRole(Role.Tab) and hasText(text = BottomNavItem.PreferencesDataStore.title),
            ).assertIsSelected()
        }
    }
}
