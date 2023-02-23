package com.adammcneilly.toa.core.ui.components.navigation

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.adammcneilly.toa.ExcludeFromJacocoGeneratedReport
import com.adammcneilly.toa.core.ui.theme.TOATheme

@Composable
fun TOABottomNavigation(
    navigationTabs: List<NavigationTabDisplayModel>,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        navigationTabs.forEach { navigationTab ->
            NavigationBarItem(
                selected = navigationTab.isSelected,
                onClick = navigationTab.onClick,
                icon = {
                    Icon(
                        imageVector = navigationTab.tab.icon,
                        contentDescription = stringResource(id = navigationTab.tab.labelTextRes),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = navigationTab.tab.labelTextRes),
                    )
                },
            )
        }
    }
}

@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun TOABottomNavigationPreview() {
    val tabs = listOf(
        NavigationTabDisplayModel(
            tab = NavigationTab.Home,
            isSelected = true,
            onClick = {},
        ),
        NavigationTabDisplayModel(
            tab = NavigationTab.Settings,
            isSelected = false,
            onClick = {},
        ),
    )

    TOATheme {
        TOABottomNavigation(
            navigationTabs = tabs,
        )
    }
}
