package com.adammcneilly.toa.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.core.ui.theme.ButtonShape
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

/**
 * A custom composable that when clicked, launches a date picker.
 */
@Composable
fun TOADatePicker(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled),
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    iconColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
) {
    val dialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("CANCEL")
        },
    ) {
        this.datepicker { newDate ->
            // Coming soon
        }
    }

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = ButtonShape,
            )
            .clickable {
                dialogState.show()
            },
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(
                text = "Today",
                color = textColor,
                modifier = Modifier
                    .weight(1F),
            )

            Icon(
                Icons.Default.DateRange,
                contentDescription = "Select Date",
                tint = iconColor,
            )
        }
    }
}

@Composable
private fun md3DatePickerColors(
    headerBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    headerTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    activeBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    inactiveBackgroundColor: Color = Color.Transparent,
    activeTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    inactiveTextColor: Color = MaterialTheme.colorScheme.onBackground,
): DatePickerColors {
    return DatePickerDefaults.colors(
        headerBackgroundColor = headerBackgroundColor,
        headerTextColor = headerTextColor,
        activeBackgroundColor = activeBackgroundColor,
        inactiveBackgroundColor = inactiveBackgroundColor,
        activeTextColor = activeTextColor,
        inactiveTextColor = inactiveTextColor,
    )
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
private fun TOADatePickerPreview() {
    TOATheme {
        Surface {
            TOADatePicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
        }
    }
}
