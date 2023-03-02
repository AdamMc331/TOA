package com.adammcneilly.toa.core.ui.components.navigation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adammcneilly.toa.ExcludeFromJacocoGeneratedReport
import com.adammcneilly.toa.core.ui.theme.TOATheme
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@Composable
fun TOANavigationDrawerContent(
    navHostController: NavHostController,
    tabs: List<NavigationTab>,
    modifier: Modifier = Modifier,
) {
    val currentRoute = navHostController.currentDestinationAsState().value?.route

    val shouldShowNavigationDrawer = tabs.any { tab ->
        tab.screenRoute == currentRoute
    }

    if (shouldShowNavigationDrawer) {
        TOANavigationDrawerContent(
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
private fun TOANavigationDrawerContent(
    tabs: List<NavigationTab>,
    selectedTab: NavigationTab?,
    onTabClicked: (NavigationTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    PermanentDrawerSheet(
        modifier = modifier.sizeIn(
            minWidth = 200.dp,
            maxWidth = 200.dp,
        ),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            tabs.forEach { tab ->
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = stringResource(id = tab.labelTextRes),
                        )
                    },
                    selected = (tab == selectedTab),
                    onClick = {
                        onTabClicked.invoke(tab)
                    },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = stringResource(id = tab.labelTextRes),
                        )
                    }
                )
            }
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
private fun TOANavigationDrawerContentPreview() {
    TOATheme {
        TOANavigationDrawerContent(
            tabs = listOf(NavigationTab.Home, NavigationTab.Settings),
            selectedTab = NavigationTab.Home,
            onTabClicked = {},
        )
    }
}
