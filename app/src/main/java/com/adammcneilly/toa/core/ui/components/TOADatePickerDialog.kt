package com.adammcneilly.toa.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.adammcneilly.toa.toEpochMillisUTC
import java.time.LocalDate

/**
 * This is a [Dialog] that will render a [DatePicker] and allow the user to select any date today
 * or in the future. This is most likely going to be triggered from within a [TOADatePickerInput].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TOADatePickerDialog(
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onComplete: (Long?) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface {
            Column {
                DatePicker(
                    datePickerState,
                    dateValidator = { dateMillis ->
                        val todayStartMillis = LocalDate.now().toEpochMillisUTC()

                        dateMillis >= todayStartMillis
                    },
                )

                DatePickerButtonRow(
                    onCancelClicked = onDismiss,
                    onDoneClicked = {
                        onComplete(datePickerState.selectedDateMillis)
                    },
                )
            }
        }
    }
}

@Composable
private fun DatePickerButtonRow(
    onCancelClicked: () -> Unit,
    onDoneClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onCancelClicked,
        ) {
            Text("CANCEL")
        }

        Spacer(modifier = Modifier.width(8.dp))

        TextButton(
            onClick = onDoneClicked,
        ) {
            Text("DONE")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun TOADatePickerDialogPreview() {
    TOATheme {
        TOADatePickerDialog(
            datePickerState = rememberDatePickerState(),
            onDismiss = {},
            onComplete = {},
        )
    }
}