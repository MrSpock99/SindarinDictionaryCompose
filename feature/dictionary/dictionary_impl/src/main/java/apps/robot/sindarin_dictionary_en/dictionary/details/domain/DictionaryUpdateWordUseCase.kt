package apps.robot.sindarin_dictionary_en.dictionary.details.domain

import apps.robot.sindarin_dictionary_en.dictionary.base.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.base.domain.Word
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryMode

class DictionaryUpdateWordUseCase(
    private val elfToEngDictionaryRepository: DictionaryRepository,
    private val engToElfDictionaryRepository: DictionaryRepository
) {
    suspend operator fun invoke(word: Word?, dictionaryMode: DictionaryMode?) {
        requireNotNull(word)
        when (dictionaryMode) {
            DictionaryMode.ENGLISH_TO_ELVISH -> {
                engToElfDictionaryRepository.updateWord(word)
            }
            DictionaryMode.ELVISH_TO_ENGLISH -> {
                elfToEngDictionaryRepository.updateWord(word)
            }
            null -> {
                // nothing
            }
        }
    }
}