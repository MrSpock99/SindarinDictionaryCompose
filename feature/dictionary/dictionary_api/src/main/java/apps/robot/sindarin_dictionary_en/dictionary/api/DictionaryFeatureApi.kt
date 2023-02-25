package apps.robot.sindarin_dictionary_en.dictionary.api

import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi

interface DictionaryFeatureApi : FeatureApi {
    fun listRoute(): String
    fun detailsRoute(
        wordId: String? = null,
        text: String? = null,
        translation: String? = null,
        detailsMode: String,
        dictionaryMode: String? = null
    ): String

    companion object {
        const val DETAILS_ROUTE = "dictionary/details"
    }
}