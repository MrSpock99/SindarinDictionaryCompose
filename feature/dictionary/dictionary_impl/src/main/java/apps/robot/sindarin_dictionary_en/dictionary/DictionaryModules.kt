package apps.robot.sindarin_dictionary_en.dictionary

import apps.robot.sindarin_dictionary_en.dictionary.base.dictionaryBaseModule
import apps.robot.sindarin_dictionary_en.dictionary.details.detailsModule
import apps.robot.sindarin_dictionary_en.dictionary.list.dictionaryListModule

fun dictionaryModules() =
    listOf(
        dictionaryBaseModule(),
        dictionaryListModule(),
        detailsModule()
    )