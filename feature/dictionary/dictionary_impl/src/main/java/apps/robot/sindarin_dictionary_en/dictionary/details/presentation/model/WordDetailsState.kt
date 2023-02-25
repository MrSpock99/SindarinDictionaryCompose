package apps.robot.sindarin_dictionary_en.dictionary.details.presentation.model

import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.DetailsAction

internal data class WordDetailsState(
    val id: String = "",
    val word: UiText = UiText.DynamicString(""),
    val translation: UiText = UiText.DynamicString(""),
    val isFavorite: Boolean = false,
    val actions: List<DetailsAction> = listOf()
)
