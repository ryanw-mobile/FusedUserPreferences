/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.rwmobi.fuseduserpreferences.ui.theme.FusedUserPreferencesTheme

@Composable
fun IntegerSlider(
    modifier: Modifier = Modifier,
    label: String?,
    sliderPosition: Float,
    onSliderValueChange: ((Int) -> Unit),
) {
    Column(
        modifier = modifier,
    ) {
        label?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                text = it,
            )
        }

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .defaultMinSize(minHeight = 48.dp),
            value = sliderPosition,
            onValueChange = { newValue -> onSliderValueChange((newValue * 100).toInt()) },
        )
    }
}

@PreviewLightDark
@PreviewDynamicColors
@PreviewFontScale
@PreviewScreenSizes
@Composable
private fun IntegerSliderPreview(
    @PreviewParameter(LoremIpsum::class) text: String,
) {
    FusedUserPreferencesTheme {
        IntegerSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            label = text,
            sliderPosition = 50f,
            onSliderValueChange = {},
        )
    }
}
