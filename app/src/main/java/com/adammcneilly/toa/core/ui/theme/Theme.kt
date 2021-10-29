package com.adammcneilly.toa.core.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.BuildCompat

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun TOATheme(
    isDark: Boolean = isSystemInDarkTheme(),
    dynamic: Boolean = BuildCompat.isAtLeastS(),
    content: @Composable () -> Unit,
) {
    val colors = when {
        dynamic && isDark -> dynamicDarkColorScheme(LocalContext.current)
        dynamic && !isDark -> dynamicLightColorScheme(LocalContext.current)
        isDark -> DarkThemeColors
        else -> LightThemeColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}
