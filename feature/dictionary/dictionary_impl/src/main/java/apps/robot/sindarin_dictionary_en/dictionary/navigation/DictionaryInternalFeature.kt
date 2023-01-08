package apps.robot.sindarin_dictionary_en.dictionary.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi
import apps.robot.sindarin_dictionary_en.dictionary.api.DictionaryFeatureApi
import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.composable.WordDetails
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryMode

class DictionaryInternalFeature : FeatureApi {
    private val wordId = "word_id"
    private val dictionaryMode = "dictionary_mode"

    fun detailsScreen(wordId: String, dictionaryMode: String) = detailsScreenRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.navigation(
            route = detailsScreenRoute,
            startDestination = detailsScreenRoute
        ) {
            composable(
                route = detailsScreenRoute,
                arguments = listOf(
                    navArgument(wordId) { type = NavType.StringType },
                    navArgument(dictionaryMode) { type = NavType.StringType }
                )
            ) {
                val arguments = requireNotNull(it.arguments)
                val wordId = arguments.getString(wordId)
                val dictionaryMode = arguments.getString(dictionaryMode)

                WordDetails(
                    wordId = wordId.orEmpty(),
                    dictionaryMode = DictionaryMode.valueOf(dictionaryMode.orEmpty()),
                    navigator = navController
                )
            }
        }
    }

    companion object {
        const val detailsScreenRoute = "dictionary/details"
    }
}