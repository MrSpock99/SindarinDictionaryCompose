package apps.robot.grammar.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import apps.robot.grammar.api.GrammarFeatureApi
import apps.robot.grammar.impl.pronounce.presentation.composable.PronounceScreen

internal class GrammarFeatureApiImpl(): GrammarFeatureApi {

    override fun grammarRoute(): String = "grammar_route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(grammarRoute()) {
           PronounceScreen()
        }
    }
}