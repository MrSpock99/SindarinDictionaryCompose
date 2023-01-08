package apps.robot.sindarin_dictionary_en.bottomnav.navigation

import apps.robot.monolith.R

sealed class BottomUiTab(
    val direction: String,
    val icon: Int,
    val title: Int
)

object DictionaryBottomUiTab : BottomUiTab(
    "dictionary",
    R.drawable.bottom_tab_dictionary_ic,
    R.string.bottom_tab_dictionary
)

object PhrasebookBottomUiTab : BottomUiTab(
    "phrasebook",
    R.drawable.bottom_tab_phrasebook_ic,
    R.string.bottom_tab_phrasebook
)

object GrammarBottomUiTab : BottomUiTab(
    "grammar",
    R.drawable.bottom_tab_grammar_ic,
    R.string.bottom_tab_grammar
)

object FavoritesBottomUiTab : BottomUiTab(
    "favorites",
    R.drawable.bottom_tab_favorites_ic,
    R.string.bottom_tab_favorites
)