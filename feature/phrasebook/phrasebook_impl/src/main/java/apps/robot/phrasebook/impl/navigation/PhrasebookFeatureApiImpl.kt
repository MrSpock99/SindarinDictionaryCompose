package apps.robot.phrasebook.impl.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import apps.robot.phrasebook.api.PhrasebookFeatureApi
import apps.robot.phrasebook.impl.categories.presentation.PhrasebookCategoriesList

class PhrasebookFeatureApiImpl : PhrasebookFeatureApi {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(phrasebookRoute()) {
            PhrasebookCategoriesList()
        }
    }

    override fun phrasebookRoute(): String = "phrasebook_route"
}