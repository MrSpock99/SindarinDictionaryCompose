package apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavHostFlow(
    navControllerStorage: NavControllerStorage,
    startDestination: String,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    val navController = rememberNavController()
    DisposableEffect(navController) {
        navControllerStorage.navController = navController
        onDispose {
            navControllerStorage.navController = null
        }
    }
    NavHost(navController, startDestination, modifier, route, builder)
}