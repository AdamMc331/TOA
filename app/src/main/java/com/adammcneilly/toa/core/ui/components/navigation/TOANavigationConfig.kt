package com.adammcneilly.toa.core.ui.components.navigation

/**
 * All forms of navigation within the TOA app share the same
 * core functionality of showing some [NavigationTab] entities and
 * handling their click events.
 */
data class TOANavigationConfig(
    val tabs: List<NavigationTab>,
    val selectedTab: NavigationTab?,
    val onTabClicked: (NavigationTab) -> Unit,
)
