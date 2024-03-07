package com.adammcneilly.toa.core.ui.components

import android.content.res.Configuration
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.adammcneilly.toa.ExcludeFromJacocoGeneratedReport
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
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onComplete.invoke(datePickerState.selectedDateMillis)
                },
            ) {
                Text(
                    text = "DONE",
                )
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}

object TOADatePickerDialog {
    /**
     * Custom implementation of [SelectableDates] that restricts to only select dates
     * in the future.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    val FutureDates = object : SelectableDates {
        override fun isSelectableDate(
            utcTimeMillis: Long,
        ): Boolean {
            val todayStartMillis = LocalDate.now().toEpochMillisUTC()
            return utcTimeMillis >= todayStartMillis
        }

        override fun isSelectableYear(
            year: Int,
        ): Boolean {
            val todayYear = LocalDate.now().year
            return year >= todayYear
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
@ExcludeFromJacocoGeneratedReport
private fun TOADatePickerDialogPreview() {
    TOATheme {
        TOADatePickerDialog(
            datePickerState = rememberDatePickerState(),
            onDismiss = {},
            onComplete = {},
        )
    }
}
