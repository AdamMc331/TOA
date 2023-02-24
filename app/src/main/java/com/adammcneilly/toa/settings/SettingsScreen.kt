package com.adammcneilly.toa.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun SettingsScreen(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Settings Screen Stub",
        modifier = modifier,
    )
}
