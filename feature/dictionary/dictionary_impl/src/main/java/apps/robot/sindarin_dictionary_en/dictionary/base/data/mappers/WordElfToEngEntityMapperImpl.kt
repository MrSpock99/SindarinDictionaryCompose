package apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers

import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.base.domain.Word

class WordElfToEngEntityMapperImpl : WordElfToEngEntityMapper {
    override fun map(word: Word): ElfToEngWordEntity {
        return ElfToEngWordEntity(
            id = word.id,
            word = word.word,
            translation = word.translation,
            isFavorite = word.isFavorite
        )
    }
}