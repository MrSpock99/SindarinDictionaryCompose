package apps.robot.favorites.api

import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi

interface FavoritesFeatureApi: FeatureApi {
    fun favoritesRoute(): String

    companion object {
        const val DETAILS_ROUTE = "dictionary/details"
    }
}