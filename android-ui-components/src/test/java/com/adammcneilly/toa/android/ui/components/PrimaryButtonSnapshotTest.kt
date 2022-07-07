package com.adammcneilly.toa.android.ui.components

import android.widget.LinearLayout
import app.cash.paparazzi.Paparazzi
import androidx.compose.material3.Text
import org.junit.Rule
import org.junit.Test

class PrimaryButtonSnapshotTest {
    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun renderEnabledButton() {
        val view = paparazzi.inflate<LinearLayout>(R.layout.test_layout)

        paparazzi.snapshot(view)
    }

    @Test
    fun renderComposable() {
        paparazzi.snapshot {
            Text("Hey")
        }
    }
}
