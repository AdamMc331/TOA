package com.adammcneilly.toa.core.ui.components.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@Composable
fun TOABottomNavigation(
    navHostController: NavHostController,
    tabs: List<NavigationTab>,
    modifier: Modifier = Modifier,
) {
    val currentRoute = navHostController.currentDestinationAsState().value?.route

    val shouldShowBottomBar = tabs.any { tab ->
        tab.screenRoute == currentRoute
    }

    if (shouldShowBottomBar) {
        NavigationBar(
            modifier = modifier,
        ) {
            tabs.forEach { tab ->
                NavigationBarItem(
                    selected = tab.screenRoute == currentRoute,
                    onClick = {
                        if (tab.screenRoute != currentRoute) {
                            navHostController.navigate(tab.screenRoute)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = stringResource(id = tab.labelTextRes),
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = tab.labelTextRes),
                        )
                    },
                )
            }
        }
    }
}
