package apps.robot.favorites.impl.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import apps.robot.favorites.api.FavoritesFeatureApi
import apps.robot.favorites.impl.list.presentation.composable.FavoritesList

internal class FavoritesFeatureApiImpl(): FavoritesFeatureApi {

    override fun favoritesRoute(): String = "favorites_route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(favoritesRoute()) {
            FavoritesList(navigator = navController)
        }
    }
}