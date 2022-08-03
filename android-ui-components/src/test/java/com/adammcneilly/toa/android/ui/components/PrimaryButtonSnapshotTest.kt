package com.adammcneilly.toa.android.ui.components

import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test

class PrimaryButtonSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun renderEnabledButton() {
        paparazzi.snapshot {
            PrimaryButton(
                text = "Enabled Button",
                onClick = { /*TODO*/ },
                enabled = true,
            )
        }
    }
}
