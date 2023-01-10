package apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers

import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.base.domain.Word

interface WordElfToEngEntityMapper {
    fun map(word: Word): ElfToEngWordEntity
}