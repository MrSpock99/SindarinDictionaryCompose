package apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers

import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.base.domain.Word

internal class WordEngToElfEntityMapperImpl : WordEngToElfEntityMapper {
    override fun map(word: Word): EngToElfWordEntity {
        return EngToElfWordEntity(
            id = word.id,
            word = word.word,
            translation = word.translation,
            isFavorite = word.isFavorite
        )
    }
}