package com.adammcneilly.toa.core.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.adammcneilly.toa.R
import com.adammcneilly.toa.destinations.SettingsScreenDestination
import com.adammcneilly.toa.destinations.TaskListScreenDestination

/**
 * Represents a tab that can appear on some navigation component. This just defines the information
 * that we'll always have, state such as if the tab is selected is represented over in
 * [NavigationTabDisplayModel].
 */
sealed class NavigationTab(
    val labelTextRes: Int,
    val icon: ImageVector,
    val screenRoute: String,
) {
    object Home : NavigationTab(
        labelTextRes = R.string.home,
        icon = Icons.Default.Home,
        screenRoute = TaskListScreenDestination.route,
    )

    object Settings : NavigationTab(
        labelTextRes = R.string.settings,
        icon = Icons.Default.Settings,
        screenRoute = SettingsScreenDestination.route,
    )
}
