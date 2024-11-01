/*
 * Copyright (c) 2024. Ryan Wong
 * https://github.com/ryanw-mobile
 */

package com.rwmobi.fuseduserpreferences.ui.test

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.rwmobi.fuseduserpreferences.MainActivity

typealias FusedUserPreferencesTestRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
