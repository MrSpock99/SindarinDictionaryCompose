package apps.robot.sindarin_dictionary_en.bottomnav.navigation

import apps.robot.sindarin_dictionary_en.R
import apps.robot.sindarin_dictionary_en.destinations.DictionaryListDestination
import apps.robot.sindarin_dictionary_en.destinations.DirectionDestination
import apps.robot.sindarin_dictionary_en.destinations.PhrasebookScreenDestination

sealed class BottomUiTab(
    val direction: DirectionDestination,
    val icon: Int,
    val title: Int
)

object DictionaryBottomUiTab : BottomUiTab(
    DictionaryListDestination,
    R.drawable.bottom_tab_dictionary_ic,
    R.string.bottom_tab_dictionary
)

object PhrasebookBottomUiTab : BottomUiTab(
    PhrasebookScreenDestination,
    R.drawable.bottom_tab_phrasebook_ic,
    R.string.bottom_tab_phrasebook
)

object GrammarBottomUiTab : BottomUiTab(
    PhrasebookScreenDestination,
    R.drawable.bottom_tab_grammar_ic,
    R.string.bottom_tab_grammar
)

object FavoritesBottomUiTab : BottomUiTab(
    PhrasebookScreenDestination,
    R.drawable.bottom_tab_favorites_ic,
    R.string.bottom_tab_favorites
)