package apps.robot.sindarin_dictionary_en.dictionary.details.domain

import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word

internal class DictionaryUpdateWordUseCase(
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