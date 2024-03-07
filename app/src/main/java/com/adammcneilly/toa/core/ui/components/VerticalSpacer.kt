package com.adammcneilly.toa.core.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * This is a custom [Spacer] that consumes a [height] and removes boilerplate.
 */
@Composable
fun VerticalSpacer(
    height: Dp,
) {
    Spacer(modifier = Modifier.height(height))
}
