package apps.robot.sindarin_dictionary_en.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import apps.robot.favorites.api.FavoritesFeatureApi
import apps.robot.grammar.api.GrammarFeatureApi
import apps.robot.phrasebook.api.PhrasebookFeatureApi
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
    val phrasebookFeature = get<PhrasebookFeatureApi>()
    val grammarFeature = get<GrammarFeatureApi>()

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
            grammarFeature,
            navController = navController,
            modifier = modifier
        )

        register(
            phrasebookFeature,
            navController = navController,
            modifier = modifier
        )
    }
}