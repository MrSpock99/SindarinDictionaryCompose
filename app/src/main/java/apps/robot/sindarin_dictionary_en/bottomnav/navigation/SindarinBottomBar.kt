package apps.robot.sindarin_dictionary_en.bottomnav.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import apps.robot.sindarin_dictionary_en.NavGraphs
import apps.robot.sindarin_dictionary_en.appCurrentDestinationAsState
import apps.robot.sindarin_dictionary_en.destinations.WordDetailsDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.isRouteOnBackStack

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

    val isBottomBarVisible = when (navController.appCurrentDestinationAsState().value) {
        WordDetailsDestination -> false
        else -> true
    }
    AnimatedVisibility(
        visible = isBottomBarVisible
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface
        ) {
            tabs.forEach { destination ->
                val isCurrentDestOnBackStack = navController.isRouteOnBackStack(destination.direction)
                BottomNavigationItem(
                    selected = isCurrentDestOnBackStack,
                    onClick = {
                        if (isCurrentDestOnBackStack) {
                            // When we click again on a bottom bar item and it was already selected
                            // we want to pop the back stack until the initial destination of this bottom bar item
                            navController.popBackStack(destination.direction, false)
                            return@BottomNavigationItem
                        }

                        navController.navigate(destination.direction) {
                            // Pop up to the root of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(NavGraphs.root) {
                                saveState = true
                            }

                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
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