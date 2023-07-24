package apps.robot.phrasebook.impl.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import apps.robot.phrasebook.impl.category.presentation.composable.PhrasebookCategory
import apps.robot.phrasebook.impl.navigation.PhrasebookFeatureApiImpl.Companion.CATEGORY_ROUTE
import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi

class PhrasebookInternalFeature : FeatureApi {

    private val categoryName = "category_name"

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavHostController, modifier: Modifier) {
        navGraphBuilder.navigation(
            route = SCENARIO_DETAILS_ROUTE,
            startDestination = CATEGORY_ROUTE
        ) {
            composable(
                route = "$CATEGORY_ROUTE/{$categoryName}",
                arguments = listOf(
                    navArgument(categoryName) {
                        type = NavType.StringType
                    }
                )
            ) {
                val arguments = requireNotNull(it.arguments)
                val categoryName = arguments.getString(categoryName)
                PhrasebookCategory(categoryName = categoryName.orEmpty(), navigator = navController)
            }
        }
    }

    fun phrasebookCategoryScreen(categoryName: String) = "$CATEGORY_ROUTE/${categoryName}"

    companion object {
        private const val SCENARIO_DETAILS_ROUTE = "phrasebook/scenario_category"
    }
}