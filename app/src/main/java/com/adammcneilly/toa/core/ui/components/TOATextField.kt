package com.adammcneilly.toa.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.ExcludeFromJacocoGeneratedReport
import com.adammcneilly.toa.R
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.adammcneilly.toa.core.ui.theme.TextFieldShape

/**
 * This is a custom implementation of an [OutlinedTextField] to ensure that it has the TOA branding
 * and styling that we expect.
 *
 * @param[text] The current text inside the input.
 * @param[onTextChanged] A callback invoked whenever the user modifies the text inside this input.
 * @param[labelText] The label that shows above the input when focused.
 * @param[modifier] An optional [Modifier] to configure this component.
 * @param[focusRequester] An optional [FocusRequester] that allows the caller to control the focused
 * state of this text field.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TOATextField(
    text: String,
    onTextChanged: (String) -> Unit,
    labelText: String?,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    placeholderText: String? = null,
    focusRequester: FocusRequester = FocusRequester(),
) {
    val labelComposable: (@Composable () -> Unit)? = labelText?.let {
        @Composable {
            Text(
                text = labelText,
            )
        }
    }

    val placeholderComposable: (@Composable () -> Unit)? = placeholderText?.let {
        @Composable {
            Text(
                text = placeholderText,
            )
        }
    }

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChanged,
            label = labelComposable,
            shape = TextFieldShape,
            modifier = modifier
                .heightIn(dimensionResource(id = R.dimen.text_field_height))
                .fillMaxWidth()
                .focusRequester(focusRequester),
            isError = (errorMessage != null),
            visualTransformation = visualTransformation,
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            colors = md3TextFieldColors(),
            placeholder = placeholderComposable,
        )

        if (errorMessage != null) {
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
    }
}

@Composable
private fun md3TextFieldColors(
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    disabledTextColor: Color = textColor.copy(ContentAlpha.disabled),
    backgroundColor: Color = Color.Transparent,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    errorCursorColor: Color = MaterialTheme.colorScheme.error,
    focusedBorderColor: Color =
        MaterialTheme.colorScheme.primary.copy(alpha = ContentAlpha.high),
    unfocusedBorderColor: Color =
        MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled),
    disabledBorderColor: Color = unfocusedBorderColor.copy(alpha = ContentAlpha.disabled),
    errorBorderColor: Color = MaterialTheme.colorScheme.error,
    leadingIconColor: Color =
        MaterialTheme.colorScheme.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
    disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = ContentAlpha.disabled),
    errorLeadingIconColor: Color = leadingIconColor,
    trailingIconColor: Color =
        MaterialTheme.colorScheme.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
    disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = ContentAlpha.disabled),
    errorTrailingIconColor: Color = MaterialTheme.colorScheme.error,
    focusedLabelColor: Color =
        MaterialTheme.colorScheme.primary.copy(alpha = ContentAlpha.high),
    unfocusedLabelColor: Color = MaterialTheme.colorScheme.onSurface.copy(
        ContentAlpha.medium
    ),
    disabledLabelColor: Color = unfocusedLabelColor.copy(ContentAlpha.disabled),
    errorLabelColor: Color = MaterialTheme.colorScheme.error,
    placeholderColor: Color = MaterialTheme.colorScheme.onSurface.copy(
        ContentAlpha.medium
    ),
    disabledPlaceholderColor: Color = placeholderColor.copy(ContentAlpha.disabled),
) = outlinedTextFieldColors(
    textColor,
    disabledTextColor,
    backgroundColor,
    cursorColor,
    errorCursorColor,
    focusedBorderColor,
    unfocusedBorderColor,
    disabledBorderColor,
    errorBorderColor,
    leadingIconColor,
    disabledLeadingIconColor,
    errorLeadingIconColor,
    trailingIconColor,
    disabledTrailingIconColor,
    errorTrailingIconColor,
    focusedLabelColor,
    unfocusedLabelColor,
    disabledLabelColor,
    errorLabelColor,
    placeholderColor,
    disabledPlaceholderColor,
)

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
@ExcludeFromJacocoGeneratedReport
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
    name = "Night Mode - Error",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode - Error",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("UnusedPrivateMember")
@ExcludeFromJacocoGeneratedReport
private fun ErrorTOATextFieldPreview() {
    TOATheme {
        Surface {
            TOATextField(
                text = "TOA text field",
                onTextChanged = {},
                labelText = "Label",
                errorMessage = "Plz enter this",
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
@ExcludeFromJacocoGeneratedReport
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
