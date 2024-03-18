/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.rwmobi.fuseduserpreferences.ui.components.BooleanSwitch
import com.rwmobi.fuseduserpreferences.ui.components.IntegerSlider
import com.rwmobi.fuseduserpreferences.ui.components.StringTextField

@Composable
@PreviewScreenSizes
fun PreferenceScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(
                state = rememberScrollState(),
                enabled = true,
            )
            .padding(all = 32.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        StringTextField()
        BooleanSwitch()
        IntegerSlider()
    }
}
