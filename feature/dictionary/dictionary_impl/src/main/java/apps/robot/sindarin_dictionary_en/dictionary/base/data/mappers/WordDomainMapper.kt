package apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers

import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word

internal interface WordDomainMapper {
    fun map(elfToEngWordEntity: ElfToEngWordEntity): Word
    fun map(engToElfWordEntity: EngToElfWordEntity): Word
    fun map(entity: Any): Word
}