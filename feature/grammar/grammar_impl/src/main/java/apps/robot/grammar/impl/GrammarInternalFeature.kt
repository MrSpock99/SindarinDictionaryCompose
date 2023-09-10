package apps.robot.grammar.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import apps.robot.grammar.impl.GrammarFeatureApiImpl.Companion.PLURAL_ROUTE
import apps.robot.grammar.impl.GrammarFeatureApiImpl.Companion.PRONOUNCE_ROUTE
import apps.robot.grammar.impl.GrammarFeatureApiImpl.Companion.STRESSES_ROUTE
import apps.robot.grammar.impl.plural.presentation.composable.PluralScreen
import apps.robot.grammar.impl.pronounce.presentation.composable.PronounceScreen
import apps.robot.grammar.impl.stresses.presentation.composable.StressesScreen
import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi

internal class GrammarInternalFeature : FeatureApi {

    fun pronounceScreen() = PRONOUNCE_ROUTE
    fun stressesScreen() = STRESSES_ROUTE

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
            composable(
                route = STRESSES_ROUTE
            ) {
                StressesScreen(navController)
            }
            composable(
                route = PLURAL_ROUTE
            ) {
                PluralScreen(navController)
            }
        }
    }
}