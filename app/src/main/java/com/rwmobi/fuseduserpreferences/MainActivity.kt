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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.rwmobi.fuseduserpreferences.ui.screens.PreferenceScreen
import com.rwmobi.fuseduserpreferences.ui.theme.FusedUserPreferencesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

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
                        text = "",
                        onTextfieldValueChange = {},
                        switchChecked = false,
                        onCheckChange = {},
                        sliderPosition = 0f,
                        onSliderValueChange = {},
                    )
                }
            }
        }
    }
}
