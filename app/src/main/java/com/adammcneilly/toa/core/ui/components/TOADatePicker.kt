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
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.adammcneilly.toa.ExcludeFromJacocoGeneratedReport
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.ui.theme.ButtonShape
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.adammcneilly.toa.toEpochMillis
import com.adammcneilly.toa.toLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * A custom composable that when clicked, launches a date picker.
 */
@OptIn(ExperimentalMaterial3Api::class)
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
    val showDatePicker = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = value.toEpochMillis(),
    )

    if (showDatePicker.value) {
        Dialog(
            onDismissRequest = {
                showDatePicker.value = false
            },
        ) {
            Surface {
                Column {
                    DatePicker(
                        datePickerState,
                        dateValidator = { dateMillis ->
                            dateMillis >= System.currentTimeMillis()
                        },
                    )

                    Row {
                        TextButton(
                            onClick = {
                                showDatePicker.value = false
                            },
                        ) {
                            Text("CANCEL")
                        }

                        TextButton(
                            onClick = {
                                showDatePicker.value = false

                                datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                                    val localDate = selectedDateMillis.toLocalDate()
                                    onValueChanged.invoke(localDate)
                                }
                            },
                        ) {
                            Text("DONE")
                        }
                    }
                }
            }
        }
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
                    showDatePicker.value = true
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
@ExcludeFromJacocoGeneratedReport
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
@ExcludeFromJacocoGeneratedReport
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
