package apps.robot.grammar.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import apps.robot.grammar.impl.GrammarFeatureApiImpl.Companion.PRONOUNCE_ROUTE
import apps.robot.grammar.impl.pronounce.presentation.composable.PronounceScreen
import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi

internal class GrammarInternalFeature : FeatureApi {

    fun pronounceScreen() = PRONOUNCE_ROUTE

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavHostController, modifier: Modifier) {
        navGraphBuilder.navigation(
            route = "grammar/pronounce_scenario",
            startDestination = PRONOUNCE_ROUTE
        ) {
            composable(
                route = PRONOUNCE_ROUTE
            ) {
                PronounceScreen(navController)
            }
        }
    }
}