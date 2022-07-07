package com.adammcneilly.toa.android.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp

/**
 * This is a custom [Button] that provides the shape and styling expected
 * in the TOA application.
 *
 * @param[text] The text inside the button.
 * @param[onClick] A callback invoked when the user clicks the button.
 * @param[modifier] An optional [Modifier] to configure this component.
 * @param[containerColor] The color of the button in an enabled state.
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        enabled = enabled,
    ) {
        Text(
            text = text.toUpperCase(Locale.current),
        )
    }
}
