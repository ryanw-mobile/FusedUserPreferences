/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.rwmobi.fuseduserpreferences.ui.components.BooleanSwitch
import com.rwmobi.fuseduserpreferences.ui.components.IntegerSlider
import com.rwmobi.fuseduserpreferences.ui.components.StringTextField

@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    text: String,
    onTextfieldValueChange: ((String) -> Unit),
    switchChecked: Boolean,
    onCheckChange: ((Boolean) -> Unit),
    sliderPosition: Float,
    onSliderValueChange: ((Float) -> Unit),
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
        StringTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Stored value = $text",
            value = text,
            onValueChange = onTextfieldValueChange,
        )

        BooleanSwitch(
            modifier = Modifier.fillMaxWidth(),
            label = "Stored value = $switchChecked",
            checked = switchChecked,
            onCheckedChange = onCheckChange,
        )

        IntegerSlider(
            modifier = Modifier.fillMaxWidth(),
            label = "Stored value = $sliderPosition",
            sliderPosition = sliderPosition,
            onSliderValueChange = onSliderValueChange,
        )
    }
}

@PreviewLightDark
@PreviewDynamicColors
@PreviewFontScale
@PreviewScreenSizes
@Composable
private fun PreferenceScreenPreview(
    @PreviewParameter(LoremIpsum::class) text: String,
) {
    MaterialTheme {
        PreferenceScreen(
            modifier = Modifier
                .fillMaxWidth()
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
