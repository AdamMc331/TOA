package com.adammcneilly.toa.core.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@Composable
fun TOANavigationContainer(
    navHostController: NavHostController,
    tabs: List<NavigationTab>,
    navigationType: NavigationType,
    modifier: Modifier = Modifier,
) {
    val currentRoute = navHostController.currentDestinationAsState().value?.route

    val shouldShowNavigation = tabs.any { tab ->
        tab.screenRoute == currentRoute
    }

    val selectedTab = tabs.find { tab ->
        tab.screenRoute == currentRoute
    }

    val onTabClicked = { tab: NavigationTab ->
        if (tab.screenRoute != currentRoute) {
            navHostController.navigate(tab.screenRoute)
        }
    }

    val navigationConfig = TOANavigationConfig(
        tabs = tabs,
        selectedTab = selectedTab,
        onTabClicked = onTabClicked,
    )

    if (shouldShowNavigation) {
        when (navigationType) {
            NavigationType.BOTTOM_NAVIGATION -> {
                TOABottomNavigation(
                    navigationConfig,
                    modifier,
                )
            }
            NavigationType.NAVIGATION_RAIL -> {
                TOANavigationRail(
                    navigationConfig,
                    modifier,
                )
            }
            NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
                TOANavigationDrawerContent(
                    navigationConfig,
                    modifier,
                )
            }
        }
    }
}
