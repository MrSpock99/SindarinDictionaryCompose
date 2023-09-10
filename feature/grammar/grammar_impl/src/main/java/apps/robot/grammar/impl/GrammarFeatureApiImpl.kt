package apps.robot.grammar.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import apps.robot.grammar.api.GrammarFeatureApi
import apps.robot.grammar.impl.grammar.presentation.composable.GrammarListScreen

internal class GrammarFeatureApiImpl(
    private val grammarInternalFeature: GrammarInternalFeature
): GrammarFeatureApi {

    override fun grammarRoute(): String = GRAMMAR_ROUTE
    override fun pronounceRoute(): String {
        return grammarInternalFeature.pronounceScreen()
    }

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(grammarRoute()) {
           GrammarListScreen(navigator = navController)
        }

        grammarInternalFeature.registerGraph(
            navGraphBuilder = navGraphBuilder,
            navController = navController,
            modifier = modifier
        )
    }

    companion object {
        const val GRAMMAR_ROUTE = "grammar_route"
        const val PRONOUNCE_ROUTE = "$GRAMMAR_ROUTE/pronounce"
    }
}