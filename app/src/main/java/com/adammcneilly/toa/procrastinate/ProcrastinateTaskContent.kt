package com.adammcneilly.toa.procrastinate

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        TomorrowOption(state)

        NextWeekOption(state)

        FutureOption(state)

        ProcrastinateButton()
    }
}

@Composable
private fun ProcrastinateButton() {
    PrimaryButton(
        text = "Procrastinate",
        onClick = { /*TODO*/ },
    )
}

@Composable
private fun FutureOption(
    state: ProcrastinateTaskViewState,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val isSelected = (state.selectedOption is ProcrastinateOption.Future)
        RadioOption(
            isSelected = isSelected,
            text = "Future Problem",
        )

        TOADatePickerInput(
            value = LocalDate.now().plusMonths(1),
            onValueChanged = {},
            enabled = isSelected,
        )
    }
}

@Composable
private fun NextWeekOption(
    state: ProcrastinateTaskViewState,
) {
    RadioOption(
        isSelected = (state.selectedOption == ProcrastinateOption.NextWeek),
        text = "Next Week's Problem",
    )
}

@Composable
private fun TomorrowOption(
    state: ProcrastinateTaskViewState,
) {
    RadioOption(
        isSelected = (state.selectedOption == ProcrastinateOption.Tomorrow),
        text = "Tomorrow's Problem",
    )
}

@Composable
private fun RadioOption(
    isSelected: Boolean,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { /*TODO*/ },
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
            )
        }
    }
}
