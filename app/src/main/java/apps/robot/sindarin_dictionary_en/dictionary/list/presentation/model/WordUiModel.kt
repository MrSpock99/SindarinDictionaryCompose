package apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model

import apps.robot.base_ui.presentation.UiText

class WordUiModel(
    val id: String,
    val word: UiText,
    val translation: UiText,
    val isFavorite: Boolean
)