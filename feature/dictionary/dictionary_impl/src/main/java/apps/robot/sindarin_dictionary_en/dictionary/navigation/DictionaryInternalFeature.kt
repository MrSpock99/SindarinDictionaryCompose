package apps.robot.sindarin_dictionary_en.dictionary.navigation

import android.net.Uri
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi
import apps.robot.sindarin_dictionary_en.dictionary.api.DictionaryFeatureApi.Companion.DETAILS_ROUTE
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DetailsMode
import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.composable.WordDetails

internal class DictionaryInternalFeature : FeatureApi {
    private val wordId = "word_id"
    private val text = "text"
    private val translation = "translation"
    private val detailsMode = "details_mode"

    fun detailsScreen(
        wordId: String?,
        text: String? = null,
        translation: String? = null,
        detailsMode: String
    ) = "$DETAILS_ROUTE/$wordId/${Uri.encode(text)}/${Uri.encode(translation)}/$detailsMode"

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
                route = "$DETAILS_ROUTE/{$wordId}/{$text}/{$translation}/{$detailsMode}",
                arguments = listOf(
                    navArgument(wordId) {
                        nullable = true
                        type = NavType.StringType
                    },
                    navArgument(text) {
                        nullable = true
                        type = NavType.StringType
                    },
                    navArgument(translation) {
                        nullable = true
                        type = NavType.StringType
                    },
                    navArgument(detailsMode) {
                        type = NavType.StringType
                    },
                )
            ) {
                val arguments = requireNotNull(it.arguments)
                val wordId = arguments.getString(wordId)
                val text = arguments.getString(text)
                val translation = arguments.getString(translation)
                val detailsMode = arguments.getString(detailsMode)

                WordDetails(
                    wordId = wordId.orEmpty(),
                    text = text,
                    translation = translation,
                    navigator = navController,
                    detailsMode = DetailsMode.valueOf(detailsMode.orEmpty())
                )
            }
        }
    }

    companion object {
        private const val SCENARIO_DETAILS_ROUTE = "dictionary/scenario_details"
    }
}