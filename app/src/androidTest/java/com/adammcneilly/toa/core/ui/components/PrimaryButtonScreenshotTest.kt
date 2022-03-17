package com.adammcneilly.toa.core.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test

class PrimaryButtonScreenshotTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun renderEnabledButton() {
        composeTestRule.setContent {
            TOATheme {
                PrimaryButton(
                    text = "Enabled Button",
                    onClick = {},
                    enabled = true,
                )
            }
        }

        compareScreenshot(composeTestRule)
    }

    @Test
    fun renderDisabledButton() {
        composeTestRule.setContent {
            TOATheme {
                PrimaryButton(
                    text = "Disabled Button",
                    onClick = {},
                    enabled = false,
                )
            }
        }

        compareScreenshot(composeTestRule)
    }
}
