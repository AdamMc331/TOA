package com.adammcneilly.toa.ui.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adammcneilly.toa.ui.components.PrimaryButton
import com.adammcneilly.toa.ui.components.SecondaryButton
import com.adammcneilly.toa.ui.theme.TOATheme

/**
 * This composable maintains the entire screen for handling user login.
 *
 * @param[viewState] The current state of the screen to render.
 */
@Composable
fun LoginContent() {
    Scaffold(
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
        ) {

            Spacer(modifier = Modifier.weight(1F))

            PrimaryButton(
                text = "log in",
                onClick = { /*TODO*/ },
                backgroundColor = MaterialTheme.colors.secondary,
            )

            Spacer(modifier = Modifier.height(12.dp))

            SecondaryButton(
                text = "sign up",
                onClick = { /*TODO*/ },
                contentColor = MaterialTheme.colors.onPrimary,
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
        LoginContent()
    }
}
