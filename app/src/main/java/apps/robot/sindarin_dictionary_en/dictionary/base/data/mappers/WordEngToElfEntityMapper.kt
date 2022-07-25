package apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers

import apps.robot.sindarin_dictionary_en.dictionary.base.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.base.domain.Word

interface WordEngToElfEntityMapper {
    fun map(word: Word): EngToElfWordEntity
}