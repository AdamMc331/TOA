package com.adammcneilly.toa.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.ui.theme.ButtonShape
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * A custom composable that when clicked, launches a date picker.
 */
@Composable
fun TOADatePicker(
    value: LocalDate,
    onValueChanged: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled),
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    iconColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
    errorMessage: String? = null,
) {
    val dialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(R.string.ok))
            negativeButton(stringResource(R.string.cancel))
        },
    ) {
        this.datepicker(
            initialDate = value,
            onDateChange = onValueChanged,
        )
    }

    val hasError = errorMessage != null

    val borderColorToUse = if (hasError) {
        MaterialTheme.colorScheme.error
    } else {
        borderColor
    }

    val iconColorToUse = if (hasError) {
        MaterialTheme.colorScheme.error
    } else {
        iconColor
    }

    Column(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = borderColorToUse,
                    shape = ButtonShape,
                )
                .clip(ButtonShape)
                .clickable {
                    dialogState.show()
                },
        ) {
            DateAndIcon(
                value = value,
                textColor = textColor,
                iconColorToUse = iconColorToUse,
            )
        }

        if (errorMessage != null) {
            ErrorMessage(errorMessage)
        }
    }
}

@Composable
private fun DateAndIcon(
    value: LocalDate,
    textColor: Color,
    iconColorToUse: Color,
) {
    Row(
        modifier = Modifier
            .padding(16.dp),
    ) {
        Text(
            text = value.toUIString(),
            color = textColor,
            modifier = Modifier
                .weight(1F),
        )

        Icon(
            Icons.Default.DateRange,
            contentDescription = stringResource(R.string.select_date_content_description),
            tint = iconColorToUse,
        )
    }
}

@Composable
private fun ErrorMessage(errorMessage: String) {
    Text(
        text = errorMessage,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier
            .padding(
                top = 4.dp,
                start = 16.dp,
            ),
    )
}

private fun LocalDate.toUIString(): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
    return formatter.format(this)
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
                value = LocalDate.now(),
                onValueChanged = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
        }
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
private fun TOADatePickerWithErrorPreview() {
    TOATheme {
        Surface {
            TOADatePicker(
                value = LocalDate.now().minusDays(1),
                onValueChanged = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                errorMessage = "Scheduled date cannot be in past.",
            )
        }
    }
}
