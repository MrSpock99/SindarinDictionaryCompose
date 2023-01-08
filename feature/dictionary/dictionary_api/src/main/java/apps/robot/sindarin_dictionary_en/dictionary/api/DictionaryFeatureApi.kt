package apps.robot.sindarin_dictionary_en.dictionary.api

import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi

interface DictionaryFeatureApi: FeatureApi {
    fun listRoute(): String
}