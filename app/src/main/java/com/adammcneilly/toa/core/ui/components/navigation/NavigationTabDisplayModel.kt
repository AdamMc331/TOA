package com.adammcneilly.toa.core.ui.components.navigation

/**
 * Represents a tab to appear on some navigation component (could be a bottom bar,
 * or navigation rail).
 */
data class NavigationTabDisplayModel(
    val tab: NavigationTab,
    val isSelected: Boolean,
    val onClick: () -> Unit,
)
