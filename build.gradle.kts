/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.hilt.android.plugin) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.gradleKtlint) apply false
    alias(libs.plugins.compose.compiler) apply false
}
