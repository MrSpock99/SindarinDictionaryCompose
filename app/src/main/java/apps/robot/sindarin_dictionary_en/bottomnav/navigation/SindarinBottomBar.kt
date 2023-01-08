package apps.robot.sindarin_dictionary_en.bottomnav.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import apps.robot.sindarin_dictionary_en.dictionary.navigation.DictionaryInternalFeature

@Composable
fun SindarinBottomBar(
    navController: NavHostController,
) {
    val tabs = arrayOf(
        DictionaryBottomUiTab,
        PhrasebookBottomUiTab,
        GrammarBottomUiTab,
        FavoritesBottomUiTab
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isBottomBarVisible = currentDestination?.route?.startsWith(DictionaryInternalFeature.detailsScreenRoute)?.not() ?: false

    AnimatedVisibility(
        visible = isBottomBarVisible
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface
        ) {
            tabs.forEach { destination ->
                val isCurrentDestOnBackStack = currentDestination?.route == destination.direction
                BottomNavigationItem(
                    selected = isCurrentDestOnBackStack,
                    onClick = {
                        if (destination.direction != currentDestination?.route) {
                            navController.navigate(destination.direction) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = destination.icon),
                            tint = if (isCurrentDestOnBackStack) {
                                MaterialTheme.colors.primary
                            } else {
                                MaterialTheme.colors.onSurface
                            },
                            contentDescription = stringResource(destination.title)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(destination.title),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = if (isCurrentDestOnBackStack) {
                                MaterialTheme.colors.primary
                            } else {
                                MaterialTheme.colors.onSurface
                            }
                        )
                    }
                )
            }
        }
    }
}