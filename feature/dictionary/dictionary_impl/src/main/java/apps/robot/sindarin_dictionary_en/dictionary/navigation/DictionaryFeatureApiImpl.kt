package apps.robot.sindarin_dictionary_en.dictionary.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import apps.robot.sindarin_dictionary_en.DictionaryList
import apps.robot.sindarin_dictionary_en.dictionary.api.DictionaryFeatureApi

internal class DictionaryFeatureApiImpl(
    private val homeInternalFeature: DictionaryInternalFeature
): DictionaryFeatureApi {

    override fun listRoute() = "dictionary_list"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(listRoute()) {
            DictionaryList(navigator = navController)
        }

        homeInternalFeature.registerGraph(
            navGraphBuilder = navGraphBuilder,
            navController = navController,
            modifier = modifier
        )
    }
}