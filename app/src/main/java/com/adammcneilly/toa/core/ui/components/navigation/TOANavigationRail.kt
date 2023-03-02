package com.adammcneilly.toa.core.ui.components.navigation

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.adammcneilly.toa.ExcludeFromJacocoGeneratedReport
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@Composable
fun TOANavigationRail(
    navHostController: NavHostController,
    tabs: List<NavigationTab>,
    modifier: Modifier = Modifier,
) {
    val currentRoute = navHostController.currentDestinationAsState().value?.route

    val shouldShowNavigationRail = tabs.any { tab ->
        tab.screenRoute == currentRoute
    }

    if (shouldShowNavigationRail) {
        TOANavigationRail(
            tabs = tabs,
            selectedTab = tabs.find { tab ->
                tab.screenRoute == currentRoute
            },
            onTabClicked = { tab ->
                if (tab.screenRoute != currentRoute) {
                    navHostController.navigate(tab.screenRoute)
                }
            },
            modifier = modifier,
        )
    }
}

@Composable
private fun TOANavigationRail(
    tabs: List<NavigationTab>,
    selectedTab: NavigationTab?,
    onTabClicked: (NavigationTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
    ) {
        tabs.forEach { tab ->
            NavigationRailItem(
                selected = tab == selectedTab,
                onClick = {
                    onTabClicked.invoke(tab)
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

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TOANavigationRailPreview() {
    TOATheme {
        TOANavigationRail(
            tabs = listOf(NavigationTab.Home, NavigationTab.Settings),
            selectedTab = NavigationTab.Home,
            onTabClicked = {},
        )
    }
}
