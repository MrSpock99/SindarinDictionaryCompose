package apps.robot.phrasebook.impl.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import apps.robot.phrasebook.api.PhrasebookFeatureApi
import apps.robot.phrasebook.impl.categories.presentation.composable.PhrasebookCategoriesList

class PhrasebookFeatureApiImpl(
    private val phrasebookInternalFeature: PhrasebookInternalFeature
) : PhrasebookFeatureApi {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(phrasebookRoute()) {
            PhrasebookCategoriesList(navigator = navController)
        }
        phrasebookInternalFeature.registerGraph(
            navGraphBuilder = navGraphBuilder,
            navController = navController,
            modifier = modifier
        )
    }

    override fun phrasebookRoute(): String = "phrasebook_route"

    companion object {
        const val CATEGORY_ROUTE = "phrasebook_route/category"
    }
}