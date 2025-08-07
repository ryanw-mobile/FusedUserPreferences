/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.rwmobi.fuseduserpreferences.ui.components.BottomNavigationBar
import com.rwmobi.fuseduserpreferences.ui.navigation.NavigationGraph
import com.rwmobi.fuseduserpreferences.ui.theme.FusedUserPreferencesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            FusedUserPreferencesTheme {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(
                        scrim = Color.Transparent.toArgb(),
                    ),
                    navigationBarStyle = SystemBarStyle.dark(
                        scrim = Color.Transparent.toArgb(),
                    ),
                )

                Surface(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            NavigationBar {
                                BottomNavigationBar(navController = navController)
                            }
                        },
                    ) { padding ->
                        NavigationGraph(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues = padding),
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}
