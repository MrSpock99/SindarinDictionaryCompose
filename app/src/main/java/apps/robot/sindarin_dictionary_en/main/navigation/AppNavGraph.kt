package apps.robot.sindarin_dictionary_en.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import apps.robot.favorites.api.FavoritesFeatureApi
import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.register
import apps.robot.sindarin_dictionary_en.dictionary.api.DictionaryFeatureApi
import org.koin.androidx.compose.get

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val dictionaryFeature = get<DictionaryFeatureApi>()
    val favoritesFeature = get<FavoritesFeatureApi>()
    NavHost(
        navController = navController,
        startDestination = dictionaryFeature.listRoute()
    ) {
        register(
            dictionaryFeature,
            navController = navController,
            modifier = modifier
        )

        register(
            favoritesFeature,
            navController = navController,
            modifier = modifier
        )

        register(
            dictionaryFeature,
            navController = navController,
            modifier = modifier
        )
    }
}