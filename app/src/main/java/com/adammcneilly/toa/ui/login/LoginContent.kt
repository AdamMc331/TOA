package com.adammcneilly.toa.ui.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.R
import com.adammcneilly.toa.ui.components.PrimaryButton
import com.adammcneilly.toa.ui.components.SecondaryButton
import com.adammcneilly.toa.ui.components.TOATextField
import com.adammcneilly.toa.ui.theme.TOATheme

/**
 * This composable maintains the entire screen for handling user login.
 *
 * @param[viewState] The current state of the screen to render.
 */
@Composable
fun LoginContent(
    viewState: LoginViewState,
) {
    Surface(
        color = MaterialTheme.colors.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.weight(1F))

            Image(
                painterResource(id = R.drawable.ic_toa_checkmark),
                contentDescription = "App Logo",
                modifier = Modifier
                    .fillMaxWidth(0.75F),
            )

            Spacer(modifier = Modifier.weight(1F))

            TOATextField(
                text = viewState.userName,
                onTextChanged = {},
                labelText = "Username",
            )

            Spacer(modifier = Modifier.height(12.dp))

            TOATextField(
                text = viewState.password,
                onTextChanged = {},
                labelText = "Password",
            )

            Spacer(modifier = Modifier.height(48.dp))

            PrimaryButton(
                text = "log in",
                onClick = { /*TODO*/ },
            )

            Spacer(modifier = Modifier.height(12.dp))

            SecondaryButton(
                text = "sign up",
                onClick = { /*TODO*/ },
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
private fun EmptyLoginContentPreview() {
    val viewState = LoginViewState(
        userName = "",
        password = "",
    )

    TOATheme {
        LoginContent(viewState)
    }
}
