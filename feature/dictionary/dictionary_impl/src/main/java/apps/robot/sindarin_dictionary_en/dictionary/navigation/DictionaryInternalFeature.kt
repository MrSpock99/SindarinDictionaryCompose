package apps.robot.sindarin_dictionary_en.dictionary.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi
import apps.robot.sindarin_dictionary_en.dictionary.api.DictionaryFeatureApi.Companion.DETAILS_ROUTE
import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.composable.WordDetails
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryMode

internal class DictionaryInternalFeature : FeatureApi {
    private val wordId = "word_id"
    private val dictionaryMode = "dictionary_mode"

    fun detailsScreen(wordId: String, dictionaryMode: String) = "$DETAILS_ROUTE/${wordId}/${dictionaryMode}"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.navigation(
            route = SCENARIO_DETAILS_ROUTE,
            startDestination = DETAILS_ROUTE
        ) {
            composable(
                route = "$DETAILS_ROUTE/{$wordId}/{$dictionaryMode}",
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
        private const val SCENARIO_DETAILS_ROUTE = "dictionary/scenario_details"
    }
}