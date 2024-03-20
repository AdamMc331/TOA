package com.adammcneilly.toa.procrastinate

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.ExcludeFromJacocoGeneratedReport
import com.adammcneilly.toa.core.ui.components.PrimaryButton
import com.adammcneilly.toa.core.ui.components.TOADatePickerInput
import com.adammcneilly.toa.core.ui.theme.TOATheme
import java.time.LocalDate

@Composable
fun ProcrastinateTaskContent(
    state: ProcrastinateTaskViewState,
    onSelectionChanged: (ProcrastinateOption) -> Unit,
    onProcrastinateClicked: () -> Unit,
    onFutureDateChanged: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        TomorrowOption(state, onSelectionChanged)

        NextWeekOption(state, onSelectionChanged)

        FutureOption(state, onSelectionChanged, onFutureDateChanged)

        Spacer(modifier = Modifier.height(8.dp))

        ProcrastinateButton(onProcrastinateClicked)
    }
}

@Composable
private fun ProcrastinateButton(
    onClick: () -> Unit,
) {
    PrimaryButton(
        text = "Procrastinate",
        onClick = onClick,
    )
}

@Composable
private fun FutureOption(
    state: ProcrastinateTaskViewState,
    onSelectionChanged: (ProcrastinateOption) -> Unit,
    onFutureDateChanged: (LocalDate) -> Unit,
) {
    val isSelected = (state.selectedOption is ProcrastinateOption.Future)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RadioButton(
            selected = isSelected,
            onClick = {
                onSelectionChanged.invoke(
                    ProcrastinateOption.Future(state.futureDate),
                )
            },
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Future problem",
            )

            TOADatePickerInput(
                value = state.futureDate,
                onValueChanged = { newDate ->
                    onFutureDateChanged.invoke(newDate)
                },
                enabled = isSelected,
            )
        }
    }
}

@Composable
private fun NextWeekOption(
    state: ProcrastinateTaskViewState,
    onSelectionChanged: (ProcrastinateOption) -> Unit,
) {
    RadioOption(
        isSelected = (state.selectedOption == ProcrastinateOption.NextWeek),
        text = "Next Week's Problem",
        onClick = {
            onSelectionChanged.invoke(ProcrastinateOption.NextWeek)
        },
    )
}

@Composable
private fun TomorrowOption(
    state: ProcrastinateTaskViewState,
    onSelectionChanged: (ProcrastinateOption) -> Unit,
) {
    RadioOption(
        isSelected = (state.selectedOption == ProcrastinateOption.Tomorrow),
        text = "Tomorrow's Problem",
        onClick = {
            onSelectionChanged.invoke(ProcrastinateOption.Tomorrow)
        },
    )
}

@Composable
private fun RadioOption(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
        )

        Text(
            text = text,
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
@ExcludeFromJacocoGeneratedReport
private fun ProcrastinateTaskContentPreview() {
    TOATheme {
        Surface {
            ProcrastinateTaskContent(
                state = ProcrastinateTaskViewState(
                    selectedOption = ProcrastinateOption.Tomorrow,
                ),
                onSelectionChanged = {},
                onProcrastinateClicked = {},
                onFutureDateChanged = {},
            )
        }
    }
}
