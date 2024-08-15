package com.example.new_kuz.navigation


import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.new_kuz.R
import com.example.new_kuz.backgroundDark
import com.example.new_kuz.backgroundLight
import com.example.new_kuz.domain.modules.inAppNav

@Composable
fun MultiNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    content: @Composable () -> Unit = {}
) {
    var selectedItem by rememberSaveable { mutableIntStateOf(1) }
    val screens = listOf(
        inAppNav.home,
        inAppNav.chat,
        inAppNav.settings)

    val colors = MaterialTheme.colorScheme.primaryContainer
    val iconColor = MaterialTheme.colorScheme.primary

    val adaptiveInfo = currentWindowAdaptiveInfo() // Custom configuration that shows a navigation drawer in large screens.
    val customNavSuiteType = with(adaptiveInfo) {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }
    }

    val color = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = colors
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            indicatorColor = colors
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = colors
        )
    )

    NavigationSuiteScaffold(
        layoutType = customNavSuiteType,
        navigationSuiteItems = {
            screens.forEachIndexed { index, item ->
                item(
                    selected = index == selectedItem,
                    onClick = {
                        selectedItem = index
                        navController.navigate(item.route)
                    },
                    icon = {
                        Icon(
                            painterResource(id = (if (index == selectedItem) item.filledIcon else item.icon)!!),
                            contentDescription = screens[index].route,
                            tint = if (index == selectedItem) iconColor else MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    label = {
                        Text(item.route)
                    },
                    colors = color,
                )
            }
        }
    ) {
        content()
    }

}

