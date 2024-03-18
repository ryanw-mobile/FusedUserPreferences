/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwebmail.com)
 */

package com.rwmobi.fuseduserpreferences.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp

@Composable
@PreviewScreenSizes
fun BooleanSwitch(
    modifier: Modifier = Modifier,
    label: String? = "223",
    checked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit) = {},
) {
    Column(
        modifier = modifier,
    ) {
        label?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                text = it,
            )
        }

        Switch(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .defaultMinSize(minHeight = 48.dp),
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}
