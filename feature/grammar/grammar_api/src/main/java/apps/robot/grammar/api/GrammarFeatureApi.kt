package apps.robot.grammar.api

import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi

interface GrammarFeatureApi: FeatureApi {
    fun grammarRoute(): String
}