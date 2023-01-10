package apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers

import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.base.domain.Word

class WordDomainMapperImpl : WordDomainMapper {
    override fun map(elfToEngWordEntity: ElfToEngWordEntity): Word {
        return Word(
            id = elfToEngWordEntity.id,
            word = elfToEngWordEntity.word,
            translation = elfToEngWordEntity.translation,
            isFavorite = elfToEngWordEntity.isFavorite
        )
    }

    override fun map(engToElfWordEntity: EngToElfWordEntity): Word {
        return Word(
            id = engToElfWordEntity.id,
            word = engToElfWordEntity.word,
            translation = engToElfWordEntity.translation,
            isFavorite = engToElfWordEntity.isFavorite
        )
    }
}