package com.adammcneilly.toa

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import dejavu.Dejavu

@HiltAndroidApp
class TOAApp : Application() {
    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)

        Dejavu.enable(
            app = this,
            logToLogcat = true,
        )
    }
}
