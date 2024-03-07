package com.adammcneilly.toa.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.ExcludeFromJacocoGeneratedReport
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.ui.theme.TOATheme

@Composable
fun SettingsContent(
    viewState: SettingsViewState,
    onNumTasksChanged: (String) -> Unit,
    onNumTasksEnabledChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.screen_padding))
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(R.string.preferences),
            style = MaterialTheme.typography.titleLarge,
        )

        NumTasksPerDayPreference(viewState, onNumTasksEnabledChanged, onNumTasksChanged)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NumTasksPerDayPreference(
    viewState: SettingsViewState,
    onNumTasksEnabledChanged: (Boolean) -> Unit,
    onNumTasksChanged: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.num_tasks_per_day_label),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1F),
        )

        Switch(
            checked = viewState.numTasksPreferenceEnabled,
            onCheckedChange = onNumTasksEnabledChanged,
        )
    }

    OutlinedTextField(
        value = viewState.numTasksPerDay?.toString().orEmpty(),
        onValueChange = onNumTasksChanged,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        modifier = Modifier
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = viewState.numTasksPreferenceEnabled,
    )
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun SettingsContentEnabledPreview() {
    val viewState = SettingsViewState(
        numTasksPerDay = 3,
        numTasksPreferenceEnabled = true,
    )

    TOATheme {
        Surface {
            SettingsContent(
                viewState = viewState,
                onNumTasksChanged = {},
                onNumTasksEnabledChanged = {},
            )
        }
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun SettingsContentDisabledPreview() {
    val viewState = SettingsViewState(
        numTasksPerDay = null,
        numTasksPreferenceEnabled = false,
    )

    TOATheme {
        Surface {
            SettingsContent(
                viewState = viewState,
                onNumTasksChanged = {},
                onNumTasksEnabledChanged = {},
            )
        }
    }
}
