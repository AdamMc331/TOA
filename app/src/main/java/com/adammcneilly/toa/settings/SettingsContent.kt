package com.adammcneilly.toa.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.screen_padding))
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Preferences",
            style = MaterialTheme.typography.titleLarge,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "Preferred number of tasks per day:",
                modifier = Modifier
                    .weight(1F),
            )

            Dropdown()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Dropdown() {
    val exposedDropdown = remember { mutableStateOf(false) }
    val preferredTasks = remember { mutableStateOf("N/A") }

    ExposedDropdownMenuBox(
        expanded = exposedDropdown.value,
        onExpandedChange = { expanded ->
            exposedDropdown.value = expanded
        },
    ) {
        BasicTextField(
            value = preferredTasks.value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                )
                .menuAnchor()
                .padding(horizontal = 16.dp)
                .width(IntrinsicSize.Min),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = LocalContentColor.current,
            ),
        )

        ExposedDropdownMenu(
            expanded = exposedDropdown.value,
            onDismissRequest = {
                exposedDropdown.value = false
            },
            modifier = Modifier
                .exposedDropdownSize(),
        ) {
            DropdownMenuItem(
                text = { Text("N/A") },
                onClick = {
                    preferredTasks.value = "N/A"
                },
            )

            for (i in 1..5) {
                DropdownMenuItem(
                    text = { Text(i.toString()) },
                    onClick = {
                        preferredTasks.value = i.toString()
                        exposedDropdown.value = false
                    },
                )
            }
        }
    }
}
