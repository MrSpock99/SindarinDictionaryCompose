package apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers

import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word

internal interface WordElfToEngEntityMapper {
    fun map(word: Word): ElfToEngWordEntity
}