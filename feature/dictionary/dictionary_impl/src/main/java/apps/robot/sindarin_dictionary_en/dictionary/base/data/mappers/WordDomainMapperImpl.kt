package apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers

import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word

internal class WordDomainMapperImpl : WordDomainMapper {
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

    override fun map(entity: Any): Word {
        return if (entity is ElfToEngWordEntity) {
            map(entity)
        } else if (entity is EngToElfWordEntity) {
            map(entity)
        } else {
            throw IllegalArgumentException()
        }
    }
}