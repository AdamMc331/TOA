package com.adammcneilly.toa.core.ui.components

import android.content.res.Configuration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import com.adammcneilly.toa.core.ui.theme.TOATheme

/**
 * This is our custom version of a [TextButton] that ensures the supplied [text] is capitalized.
 */
@Composable
fun TOATextButton(
    text: String,
    onClick: () -> Unit,
) {
    TextButton(onClick) {
        Text(
            text = text.toUpperCase(Locale.current),
            style = MaterialTheme.typography.caption,
        )
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun TOATextButtonPreview() {
    TOATheme {
        TOATextButton(
            text = "TOA Text Button",
            onClick = {},
        )
    }
}
