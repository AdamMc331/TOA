package com.adammcneilly.toa.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.adammcneilly.toa.R
import com.adammcneilly.toa.ui.theme.TOATheme
import com.adammcneilly.toa.ui.theme.TextFieldShape

/**
 * This is a custom implementation of an [OutlinedTextField] to ensure that it has the TOA branding
 * and styling that we expect.
 *
 * @param[text] The current text inside the input.
 * @param[onTextChanged] A callback invoked whenever the user modifies the text inside this input.
 * @param[labelText] The label that shows above the input when focused.
 * @param[modifier] An optional [Modifier] to configure this component.
 */
@Composable
fun TOATextField(
    text: String,
    onTextChanged: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChanged,
        label = {
            Text(
                text = labelText,
            )
        },
        shape = TextFieldShape,
        modifier = modifier
            .heightIn(dimensionResource(id = R.dimen.text_field_height))
            .fillMaxWidth(),
    )
}

@Preview(
    name = "Night Mode - Filled",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode - Filled",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun FilledTOATextFieldPreview() {
    TOATheme {
        Surface {
            TOATextField(
                text = "TOA text field",
                onTextChanged = {},
                labelText = "Label",
            )
        }
    }
}

@Preview(
    name = "Night Mode - Empty",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode - Empty",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun EmptyTOATextFieldPreview() {
    TOATheme {
        Surface {
            TOATextField(
                text = "",
                onTextChanged = {},
                labelText = "Label",
            )
        }
    }
}
