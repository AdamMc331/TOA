package com.adammcneilly.toa

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.adammcneilly.toa.login.ui.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TOATheme {
                LoginScreen(
                    onLoginCompleted = {
                        Log.d("MainActivity", "Login has been completed.")
                    },
                )
            }
        }
    }
}
