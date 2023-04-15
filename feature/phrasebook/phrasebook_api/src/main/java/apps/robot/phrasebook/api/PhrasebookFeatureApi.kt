package apps.robot.phrasebook.api

import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi

interface PhrasebookFeatureApi: FeatureApi {
    fun phrasebookRoute(): String
}