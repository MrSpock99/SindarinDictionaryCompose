package apps.robot.sindarin_dictionary_en.dictionary.details.presentation.model

import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.DetailsAction

data class WordDetailsState(
    val word: String = "",
    val translation: String = "",
    val isFavorite: Boolean = false,
    val actions: List<DetailsAction> = listOf()
)
