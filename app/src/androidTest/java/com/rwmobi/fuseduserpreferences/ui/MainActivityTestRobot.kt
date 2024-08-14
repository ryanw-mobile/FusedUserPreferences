/*
 * Copyright (c) 2024. Ryan Wong
 * https://github.com/ryanw-mobile
 */

package com.rwmobi.fuseduserpreferences.ui

import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.rwmobi.fuseduserpreferences.ui.test.XLauncherIconsTestRule

internal class MainActivityTestRobot(
    private val composeTestRule: XLauncherIconsTestRule,
) {
    // Actions
    fun printSemanticTree() {
        with(composeTestRule) {
            onRoot().printToLog("SemanticTree")
        }
    }

    // Assertions
}
